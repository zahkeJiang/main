package com.bjpygh.gzh.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.bjpygh.gzh.bean.*;
import com.bjpygh.gzh.config.AlipayConfig;
import com.bjpygh.gzh.config.MyConfig;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.*;
import com.bjpygh.gzh.utils.MD5;
import com.bjpygh.gzh.utils.PropertyUtils;
import com.bjpygh.gzh.utils.ThreeDES;
import com.github.wxpay.sdk.WXPay;
import com.jd.jr.pay.gate.signature.util.BASE64;
import com.jd.jr.pay.gate.signature.util.SignUtil;
import com.jd.jr.pay.gate.signature.util.ThreeDesUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PayController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    CouponService couponService;

    @Autowired
    PackageService packageService;

    @Autowired
    DsInfoService dsInfoService;

    @Autowired
    DsOrderService dsOrderService;

    @Autowired
    VillaOrderService villaOrderService;

    @Autowired
    ArmyOrderService armyOrderService;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatterJ = new SimpleDateFormat("yyyyMMddHHmmss");
    ThreeDES threeDES = new ThreeDES();

    @ResponseBody
    @RequestMapping(value = "/orderList", method = RequestMethod.POST)
    public Status getOrderList(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        List<DsOrder> dsOrders = dsOrderService.getOrdersById(userid);
        List<VillaOrder> villaOrders = villaOrderService.getVillaOrder(userid);
        List<ArmyOrder> armyOrders = armyOrderService.getArmyOrder(userid);
        List<UserOrder> orders = new ArrayList<UserOrder>();

        for (DsOrder dsOrder : dsOrders){
            UserOrder userOrder = new UserOrder();
            userOrder.setOrderNumber(dsOrder.getOrderNumber());
            userOrder.setOrderName(dsOrder.getDsName());
            userOrder.setOrderTime(dsOrder.getCreateTime());
            userOrder.setOrderDescripe(dsOrder.getDescription());
            userOrder.setOrderImage(dsOrder.getImageurl());
            orders.add(userOrder);
        }

        for (VillaOrder villaOrder : villaOrders){
            UserOrder userOrder = new UserOrder();
            userOrder.setOrderNumber(villaOrder.getOrderNumber());
            userOrder.setOrderName(villaOrder.getVillaName());
            userOrder.setOrderTime(villaOrder.getCreateTime());
            userOrder.setOrderDescripe(villaOrder.getNote());
            userOrder.setOrderImage(villaOrder.getImageurl());
            orders.add(userOrder);
        }

        for (ArmyOrder armyOrder : armyOrders){
            UserOrder userOrder = new UserOrder();
            userOrder.setOrderNumber(armyOrder.getOrderNumber());
            userOrder.setOrderName(armyOrder.getArmyName());
            userOrder.setOrderTime(armyOrder.getCreateTime());
            userOrder.setOrderDescripe(armyOrder.getNote());
            userOrder.setOrderImage(armyOrder.getImageurl());
            orders.add(userOrder);
        }

        if (orders.size()>0){
            Collections.sort(orders,new Comparator<UserOrder>(){
                public int compare(UserOrder arg0, UserOrder arg1) {
                    return arg0.getOrderTime().compareTo(arg1.getOrderTime());
                }
            });
            return Status.success().add("orders",orders);
        }else{
            return Status.fail(-20,"没有订单");
        }

    }

    //支付宝驾校报名支付接口
    @RequestMapping(value = "/dspay.action", method = RequestMethod.GET)
    public void DsPay(HttpServletResponse response,String ordernumber) throws IOException {

        String subject = "驾校报名费用";
        String  body = "驾校报名费用";
        String product_code="BJPYGH_DS_SIGNUP";
        DsOrder dsOrder = dsOrderService.getDsOrderByNumber(ordernumber).get(0);

        requesetAlipay(response,ordernumber,subject,body,
                dsOrder.getOrderPrice()+"",
                product_code,AlipayConfig.notify_url);

    }

    //支付宝别墅预约支付接口
    @RequestMapping(value = "/villaPay", method = RequestMethod.GET)
    public void VillaPay(HttpServletResponse response,String ordernumber) throws IOException {

        String subject = "别墅预约费用";
        String  body = "别墅预约费用";
        String product_code="BJPYGH_V_SIGNUP";
        VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(ordernumber).get(0);

        requesetAlipay(response,ordernumber,subject,body,
                villaOrder.getVillaPrice()+"",
                product_code,AlipayConfig.vnotify_url);

    }

    //支付宝军旅预约支付接口
    @RequestMapping(value = "/armyPay", method = RequestMethod.GET)
    public void ArmyPay(HttpServletResponse response,String ordernumber) throws IOException {

        String subject = "军旅预约费用";
        String  body = "军旅预约费用";
        String product_code="BJPYGH_A_SIGNUP";
        ArmyOrder armyOrder = armyOrderService.getArmyOrderByNumber(ordernumber).get(0);

        requesetAlipay(response,ordernumber,subject,body,
                armyOrder.getArmyPrice()+"",
                product_code,AlipayConfig.anotify_url);

    }

    //支付宝军旅预约支付接口
    @ResponseBody
    @RequestMapping(value = "/JdDsPay", method = RequestMethod.POST)
    public Status JdDsPay(HttpServletResponse response,String ordernumber) throws IOException{
        DsOrder dsOrder = dsOrderService.getDsOrderByNumber(ordernumber).get(0);
//        threeDES.encryptDESCBC();
        JdOrderPay jdOrderPay = new JdOrderPay();
        jdOrderPay.setVersion("V2.0");
        jdOrderPay.setMerchant("110406033");
        jdOrderPay.setTradeNum(dsOrder.getOrderNumber());
        jdOrderPay.setTradeName("驾校报名费用");
        jdOrderPay.setTradeTime(formatterJ.format(new Date()));
        jdOrderPay.setAmount(dsOrder.getOrderPrice()+"");
        jdOrderPay.setOrderType("1");
        jdOrderPay.setCurrency("CNY");
        jdOrderPay.setCallbackUrl("http://gzpt.bjpygh.com/ds_pay.html");
        jdOrderPay.setNotifyUrl("http://gzpt.bjpygh.com/jNotify_url");
        jdOrderPay.setUserId(dsOrder.getUserId()+"");

        String priKey = PropertyUtils.getProperty("wepay.merchant.rsaPrivateKey");
        String desKey = PropertyUtils.getProperty("wepay.merchant.desKey");

        List<String> unSignedKeyList = new ArrayList<String>();
        unSignedKeyList.add("sign");

        jdOrderPay.setSign(SignUtil.signRemoveSelectedKeys(jdOrderPay, priKey, unSignedKeyList));

        byte[] key = BASE64.decode(desKey);
        jdOrderPay.setUserId(ThreeDesUtil.encrypt2HexStr(key, jdOrderPay.getUserId()));
        jdOrderPay.setTradeNum(ThreeDesUtil.encrypt2HexStr(key, jdOrderPay.getTradeNum()));
        jdOrderPay.setTradeName(ThreeDesUtil.encrypt2HexStr(key, jdOrderPay.getTradeName()));
        jdOrderPay.setTradeTime(ThreeDesUtil.encrypt2HexStr(key, jdOrderPay.getTradeTime()));
        jdOrderPay.setAmount(ThreeDesUtil.encrypt2HexStr(key, jdOrderPay.getAmount()));
        jdOrderPay.setOrderType(ThreeDesUtil.encrypt2HexStr(key, jdOrderPay.getOrderType()));
        jdOrderPay.setCurrency(ThreeDesUtil.encrypt2HexStr(key, jdOrderPay.getCurrency()));
        jdOrderPay.setCallbackUrl(ThreeDesUtil.encrypt2HexStr(key, jdOrderPay.getCallbackUrl()));
        jdOrderPay.setNotifyUrl(ThreeDesUtil.encrypt2HexStr(key, jdOrderPay.getNotifyUrl()));

        return Status.success().add("jdOrderPay",jdOrderPay);
    }

    @ResponseBody
    @RequestMapping(value = "/refund.action", method = RequestMethod.POST)
    public Status DsRefund(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }

        String out_trade_no = new String(request.getParameter("ordernumber").getBytes("ISO-8859-1"),"UTF-8");

        String refund_reason=new String(request.getParameter("WIDrefund_reason").getBytes("ISO-8859-1"),"UTF-8");

        DsOrder dsOrder = dsOrderService.getDsOrderByNumber(out_trade_no).get(0);

        if(dsOrder.getOrderStatus() == 3){
            return Status.fail(-30,"报名已完成");
        }
        String refund_amount=""+dsOrder.getOrderPrice();

        if (getRefundResult(out_trade_no,refund_reason,refund_amount)){
            //改变订单状态
            dsOrder.setOrderStatus((byte) 5);
            dsOrder.setRefundTime(formatter.format(new Date()));
            dsOrderService.updateOrder(dsOrder);
            return Status.success();
        }else{
            return Status.fail(-20,"处理失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/villaRefund", method = RequestMethod.POST)
    public Status VillaRefund(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }

        String out_trade_no = new String(request.getParameter("ordernumber").getBytes("ISO-8859-1"),"UTF-8");

        String refund_reason=new String(request.getParameter("WIDrefund_reason").getBytes("ISO-8859-1"),"UTF-8");

        VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(out_trade_no).get(0);

        if(villaOrder.getOrderStatus() == 3){
            return Status.fail(-30,"报名已完成");
        }
        String refund_amount=""+villaOrder.getVillaPrice();

        if (getRefundResult(out_trade_no,refund_reason,refund_amount)){
            //改变订单状态
            villaOrder.setOrderStatus(5);
            villaOrder.setRefundTime(formatter.format(new Date()));
            villaOrderService.updateOrder(villaOrder);
            return Status.success();
        }else{
            return Status.fail(-20,"处理失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/armyRefund", method = RequestMethod.POST)
    public Status ArmyRefund(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }

        String out_trade_no = new String(request.getParameter("ordernumber").getBytes("ISO-8859-1"),"UTF-8");

        String refund_reason=new String(request.getParameter("WIDrefund_reason").getBytes("ISO-8859-1"),"UTF-8");

        ArmyOrder armyOrder = armyOrderService.getArmyOrderByNumber(out_trade_no).get(0);

        if(armyOrder.getOrderStatus() == 3){
            return Status.fail(-30,"报名已完成");
        }
        String refund_amount=""+armyOrder.getArmyPrice();

        if (getRefundResult(out_trade_no,refund_reason,refund_amount)){
            //改变订单状态
            armyOrder.setOrderStatus(5);
            armyOrder.setRefundTime(formatter.format(new Date()));
            armyOrderService.updateOrder(armyOrder);
            return Status.success();
        }else{
            return Status.fail(-20,"处理失败");
        }
    }


    //微信会员充值接口
    @ResponseBody
    @RequestMapping(value = "/wxpay.action", method = RequestMethod.POST)
    public Status WxPay(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }

        MyConfig config = null;
        try {
            config = new MyConfig();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String userid = userMap.get("id");
        String openid = userMap.get("openid");

        WXPay wxpay = new WXPay(config);
        /*閼惧嘲褰囪ぐ鎾冲閺冨爼妫块敍宀�鏁撻幋鎰吂閸楋拷*/
        Calendar c = Calendar.getInstance();//閸欘垯浜掔�佃鐦℃稉顏呮闂傛潙鐓欓崡鏇犲娣囶喗鏁�
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String out_trade_no ="PYGHHY" + year + month + date + hour + minute + second + userid;

        String total_fee = request.getParameter("total_fee");
        String key = config.getKey();
        String appid=config.getAppID();
        String mch_id = config.getMchID();
        String body = "北京漂洋过海-会员积分充值";
        String device_info = "WEB";
        String nonce_str = getRandomString(32);
        String stringA="appid="+appid+"&body="+body+"&device_info="+device_info+"&mch_id="+mch_id+"&nonce_str="+nonce_str;
        String stringSignTemp=stringA+"&key="+key; //注：key为商户平台设置的密钥key
        String sign= MD5.string2MD5(stringSignTemp); //注：MD5签名方式
//        sign=hash_hmac("sha256",stringSignTemp,key); //注：HMAC-SHA256签名方式

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", appid);
        data.put("mch_id", mch_id);
        data.put("body", body);
        data.put("out_trade_no", out_trade_no);
        data.put("device_info", device_info);
        data.put("fee_type", "CNY");
        data.put("total_fee", total_fee);
        data.put("nonce_str", nonce_str);
//        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://gzpt.bjpygh.com/notify.action");
        data.put("trade_type", "JSAPI");  // 此处指定为微信公众号支付
        data.put("openid", openid);
        data.put("sign", sign);
//        data.put("product_id", "12");

        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            String timeStamp="";
            String paySign="";
            if(resp.get("return_code").equals("SUCCESS")){
                timeStamp = ""+(new Date().getTime())/1000;
                String prepay_id = "prepay_id="+resp.get("prepay_id");
                String sA="appId="+appid+"&nonceStr="+resp.get("nonce_str")+"&package="+prepay_id+"&signType=MD5"+"&timeStamp="+timeStamp;
                String sSignTemp=sA+"&key="+key; //注：key为商户平台设置的密钥key
                paySign=MD5.string2MD5(sSignTemp); //注：MD5签名方式

            }
            return Status.success().add("timeStamp",timeStamp)
                    .add("paySign",paySign)
                    .add("data",resp);
        } catch (Exception e) {
            e.printStackTrace();
            return Status.fail(-20,"处理失败");
        }
    }

    //获取指定位数的随机字符串(包含小写字母、大写字母、数字,0<length)
    public static String getRandomString(int length) {
        //随机字符串的随机字符库
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }

    //支付宝支付请求方法
    public void requesetAlipay(HttpServletResponse response,String ordernumber,
        String subject,String body,String price,String product_code,String notify_url) throws IOException {
        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        PrintWriter out = response.getWriter();

        String timeout_express="2m";


        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();

        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(ordernumber);
        model.setSubject(subject);
        model.setTotalAmount(price);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        alipay_request.setNotifyUrl(notify_url);
        alipay_request.setReturnUrl(AlipayConfig.return_url);

        String form = "";
        try {
            form = client.pageExecute(alipay_request).getBody();
            out.write(form);
            out.flush();
            out.close();
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.flush();
            out.close();
        }
    }

    //支付宝退款请求方法
    public boolean getRefundResult(String out_trade_no,String refund_reason,String refund_amount){
        /**********************/
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        AlipayTradeRefundRequest alipay_request = new AlipayTradeRefundRequest();

        AlipayTradeRefundModel model=new AlipayTradeRefundModel();
        model.setOutTradeNo(out_trade_no);
        model.setTradeNo("");
        model.setRefundAmount(""+(float)(Math.round((Float.parseFloat(refund_amount)*0.994)*100))/100);
        model.setRefundReason(refund_reason);
        model.setOutRequestNo("PYGH01RF001");
        alipay_request.setBizModel(model);

        AlipayTradeRefundResponse alipay_response;
        try {
            alipay_response = client.execute(alipay_request);
            String result = alipay_response.getBody();
            JSONObject tmp = JSONObject.fromObject(result);
            String data = tmp.getString("alipay_trade_refund_response");
            JSONObject obj = JSONObject.fromObject(data);
            if(obj.getString("code").equals("10000")){
                if(obj.getString("fund_change").equals("Y")){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }

        } catch (AlipayApiException e) {
            return false;
        }finally{
            return false;
        }
    }


}
