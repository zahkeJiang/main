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
import com.bjpygh.gzh.entity.Refund;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.model.HttpsClientUtil;
import com.bjpygh.gzh.model.TradeRefundReqDto;
import com.bjpygh.gzh.service.*;
import com.bjpygh.gzh.utils.MD5;
import com.bjpygh.gzh.utils.PropertyUtils;
import com.bjpygh.gzh.utils.ThreeDES;
import com.bjpygh.gzh.utils.XMLToMap;
import com.github.wxpay.sdk.WXPay;
import com.jd.jr.pay.gate.signature.util.BASE64;
import com.jd.jr.pay.gate.signature.util.JdPayUtil;
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
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
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
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
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
            userOrder.setOrderDescripe(dsOrder.getDsType()+" / "
                    +dsOrder.getModels()+" / "
                    +dsOrder.getTrainTime());
            userOrder.setOrderImage(dsOrder.getImageurl());
            userOrder.setOrderStatus(dsOrder.getOrderStatus());
            userOrder.setOrderPrice(dsOrder.getOrderPrice());
            orders.add(userOrder);
        }

        for (VillaOrder villaOrder : villaOrders){
            UserOrder userOrder = new UserOrder();
            userOrder.setOrderNumber(villaOrder.getOrderNumber());
            userOrder.setOrderName(villaOrder.getNote());
            userOrder.setOrderTime(villaOrder.getCreateTime());
            String[] vs = villaOrder.getVillaName().split(",");

            userOrder.setOrderDescripe(vs.length+"栋 / "+villaOrder.getPeopleNumber()+"人 / "
                    +villaOrder.getDate().split(",").length+"晚");
            userOrder.setOrderImage(villaOrder.getImageurl());
            userOrder.setOrderStatus(villaOrder.getOrderStatus());
            userOrder.setOrderPrice(villaOrder.getVillaPrice());
            orders.add(userOrder);
        }

        for (ArmyOrder armyOrder : armyOrders){
            UserOrder userOrder = new UserOrder();
            userOrder.setOrderNumber(armyOrder.getOrderNumber());
            userOrder.setOrderName(armyOrder.getArmyName());
            userOrder.setOrderTime(armyOrder.getCreateTime());
            userOrder.setOrderDescripe(armyOrder.getPeopleNumber()+"人 / "
                    +armyOrder.getDate().split(",").length+"晚");
            userOrder.setOrderImage(armyOrder.getImageurl());
            userOrder.setOrderStatus(armyOrder.getOrderStatus());
            userOrder.setOrderPrice(armyOrder.getArmyPrice());
            orders.add(userOrder);
        }

        if (orders.size()>0){
            Collections.sort(orders,new Comparator<UserOrder>(){
                public int compare(UserOrder arg1, UserOrder arg0) {
                    return arg0.getOrderTime().compareTo(arg1.getOrderTime());
                }
            });
            return Status.success().add("orders",orders);
        }else{
            return Status.fail(-20,"没有订单");
        }

    }

    //支付宝驾校报名支付接口
    @RequestMapping(value = "/aliPay", method = RequestMethod.GET)
    public void DsPay(HttpServletResponse response,String ordernumber) throws IOException {

        String o = ordernumber.substring(0, 1);
        if (o.equals("A")) {
            ArmyOrder armyOrder = armyOrderService.getArmyOrderByNumber(ordernumber).get(0);

            requesetAlipay(response,ordernumber, "军旅预约费用", "军旅预约费用",
                    armyOrder.getArmyPrice()+"",
                    "BJPYGH_A_SIGNUP",AlipayConfig.anotify_url);
        }else if (o.equals("D")){
            DsOrder dsOrder = dsOrderService.getDsOrderByNumber(ordernumber).get(0);

            requesetAlipay(response,ordernumber,"驾校报名费用","驾校报名费用",
                    dsOrder.getOrderPrice()+"",
                    "BJPYGH_DS_SIGNUP",AlipayConfig.notify_url);
        }else if (o.equals("V")){
            VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(ordernumber).get(0);

            requesetAlipay(response,ordernumber,"别墅预约费用","别墅预约费用",
                    villaOrder.getVillaPrice()+"",
                    "BJPYGH_V_SIGNUP",AlipayConfig.vnotify_url);
        }

    }

    //别墅订单提交校验接口
    @ResponseBody
    @RequestMapping(value = "/villaCheck", method = RequestMethod.POST)
    public Status VillaCheck(String ordernumber) throws IOException {

        VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(ordernumber).get(0);
        if (villaOrderService.checkOrder(villaOrder)){
            return Status.success();
        }else{
            return Status.fail(-20,"您的套餐中别墅或日期已被预约");
        }

    }

    //预约支付接口
    @ResponseBody
    @RequestMapping(value = "/JDPay", method = RequestMethod.POST)
    public Status JdDsPay(HttpServletResponse response,String ordernumber) throws IOException{
        String o = ordernumber.substring(0, 1);
        if (o.equals("A")){
            ArmyOrder armyOrder = armyOrderService.getArmyOrderByNumber(ordernumber).get(0);
            String tradeNum = armyOrder.getOrderNumber();
            String amount = armyOrder.getArmyPrice()+"00";
            String userid =armyOrder.getUserId()+"";
            return JdPayReq(tradeNum,amount,userid,"作战之日参战费用");
        }else if (o.equals("D")){
            DsOrder dsOrder = dsOrderService.getDsOrderByNumber(ordernumber).get(0);
            String tradeNum = dsOrder.getOrderNumber();
            String amount = dsOrder.getOrderPrice()+"00";
            String userid =dsOrder.getUserId()+"";
            return JdPayReq(tradeNum,amount,userid,"驾校报名费用");
        }else if (o.equals("V")){
            VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(ordernumber).get(0);
            String tradeNum = villaOrder.getOrderNumber();
            String amount = villaOrder.getVillaPrice()+"00";
            String userid =villaOrder.getUserId()+"";
            return JdPayReq(tradeNum,amount,userid,"别墅入驻费用");
        }else {
            return Status.fail(-20,"处理失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/refund.action", method = RequestMethod.POST)
    public Status DsRefund(HttpServletRequest request, Refund refund) {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
//        String out_trade_no = new String(request.getParameter("ordernumber").getBytes("ISO-8859-1"),"UTF-8");
//        String refund_reason=new String(request.getParameter("WIDrefund_reason").getBytes("ISO-8859-1"),"UTF-8");
        String out_trade_no = refund.getOrdernumber();
        String refund_reason = refund.getWIDrefund_reason();
        String o = out_trade_no.substring(0, 1);
        if (o.equals("V")){
            VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(out_trade_no).get(0);
            //判断是否已经完成退款
            if (villaOrder.getOrderStatus() == 5){
                return Status.success();
            }
            if(villaOrder.getOrderStatus() == 4||villaOrder.getOrderStatus()==7){
                return Status.fail(-30,"订单已完成");
            }
            String refund_amount;
            long time = new Date().getTime();
            Date d = null;
            try {
                d = formatter1.parse(villaOrder.getDate().split(",")[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //判断是否再五个自然日之内
            if (d.getTime()-time<432000000L){
                //判断是否为全额付款
                if (villaOrder.getFullAmount()==1){
                    refund_amount =  ""+villaOrder.getOriginalPrice()*0.5;
                }else {
                    return Status.fail(-40,"已过退款时间");
                }
            }else {
                if (villaOrder.getFullAmount()==1){
                    refund_amount = ""+villaOrder.getOriginalPrice()*0.7;
                }else {
                    refund_amount = ""+villaOrder.getOriginalPrice()*0.2;
                }
            }
            System.out.println("refund_amount="+refund_amount);

            if (villaOrder.getPayType()==0){
                if (getRefundResult(out_trade_no,refund_reason,refund_amount)){
                    //改变订单状态
                    villaOrder.setOrderStatus(5);
                    villaOrder.setRefundTime(formatter.format(new Date()));
                    villaOrderService.updateOrder(villaOrder);
                    return Status.success();
                }else{
                    return Status.fail(-20,"处理失败");
                }
            }else if (villaOrder.getPayType()==3){
                if (getJdRefundResult(out_trade_no,refund_amount)){
                    //改变订单状态
                    villaOrder.setOrderStatus(5);
                    villaOrder.setRefundTime(formatter.format(new Date()));
                    villaOrderService.updateOrder(villaOrder);
                    return Status.success();
                }else{
                    return Status.fail(-20,"处理失败");
                }
            }else {
                return Status.fail(-30,"未知的错误发生了");
            }

        }else if (o.equals("D")){
            DsOrder dsOrder = dsOrderService.getDsOrderByNumber(out_trade_no).get(0);
            //判断是否已经完成退款
            if (dsOrder.getOrderStatus() == 5){
                return Status.success();
            }
            String refund_amount=""+(float)(Math.round((Float.parseFloat(""+dsOrder.getOrderPrice())*0.994)*100))/100;
            if(dsOrder.getOrderStatus() == 3||dsOrder.getOrderStatus() == 4||dsOrder.getOrderStatus() == 7){
                return Status.fail(-30,"报名已完成");
            }
            //判断订单由什么支付方式支付
            if (dsOrder.getPayType()==0){
                if (getRefundResult(out_trade_no,refund_reason,refund_amount)){
                    //改变订单状态
                    System.out.println("dsOrderService.refundOrder(out_trade_no)");
                    dsOrderService.refundOrder(out_trade_no);
                    return Status.success();
                }else{
                    return Status.fail(-20,"处理失败");
                }
            }else if (dsOrder.getPayType()==3){
                if (getJdRefundResult(out_trade_no,refund_amount)){
                    //改变订单状态
                    dsOrder.setOrderStatus((byte) 5);
                    dsOrder.setRefundTime(formatter.format(new Date()));
                    dsOrderService.updateOrder(dsOrder);
                    return Status.success();
                }else{
                    return Status.fail(-20,"处理失败");
                }
            }else{
                return Status.fail(-30,"未知的错误发生了");
            }
        }else if (o.equals("A")){
            ArmyOrder armyOrder = armyOrderService.getArmyOrderByNumber(out_trade_no).get(0);
            //判断是否已经完成退款
            if (armyOrder.getOrderStatus() == 5){
                return Status.success();
            }
            if(armyOrder.getOrderStatus() == 4||armyOrder.getOrderStatus() == 7){
                return Status.fail(-30,"订单已完成");
            }
            String refund_amount;
            long time = new Date().getTime();
            Date d = null;
            try {
                d = formatter1.parse(armyOrder.getDate().split(",")[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //判断是否再三个自然日之内
            if (d.getTime()-time<259200000L){
                if (armyOrder.getFullAmount()==1){
                    refund_amount = ""+armyOrder.getOriginalPrice()*0.85;
                } else {
                    refund_amount = ""+armyOrder.getOriginalPrice()*0.35;
                }
            }else {
                if (armyOrder.getFullAmount()==1){
                    refund_amount = ""+armyOrder.getArmyPrice();
                } else {
                    refund_amount = ""+armyOrder.getArmyPrice();
                }
            }

            if (armyOrder.getPayType()==0){
                if (getRefundResult(out_trade_no,refund_reason,refund_amount)){
                    //改变订单状态
                    armyOrder.setOrderStatus(5);
                    armyOrder.setRefundTime(formatter.format(new Date()));
                    armyOrderService.updateOrder(armyOrder);
                    return Status.success();
                }else{
                    return Status.fail(-20,"处理失败");
                }
            }else if (armyOrder.getPayType()==3){
                if (getJdRefundResult(out_trade_no,refund_amount)){
                    //改变订单状态
                    armyOrder.setOrderStatus(5);
                    armyOrder.setRefundTime(formatter.format(new Date()));
                    armyOrderService.updateOrder(armyOrder);
                    return Status.success();
                }else{
                    return Status.fail(-20,"处理失败");
                }
            }else{
                return Status.fail(-30,"未知的错误发生了");
            }

        }else {
            return Status.fail(-50,"订单号错误");
        }
    }

    //京东退款请求及结果获取
    private boolean getJdRefundResult(String out_trade_no, String refund_amount) {
        String deskey = PropertyUtils.getProperty("wepay.merchant.desKey");
        String priKey = PropertyUtils.getProperty("wepay.merchant.rsaPrivateKey");
        String pubKey = PropertyUtils.getProperty("wepay.jd.rsaPublicKey");

        TradeRefundReqDto tradeRefundReqDto = new TradeRefundReqDto();
        tradeRefundReqDto.setAmount((int) (Float.parseFloat(refund_amount)*100));
        tradeRefundReqDto.setVersion("V2.0");
        tradeRefundReqDto.setMerchant("110406033002");
        tradeRefundReqDto.setTradeNum(new Date().getTime()+"");
        tradeRefundReqDto.setoTradeNum(out_trade_no);
        tradeRefundReqDto.setCurrency("CNY");
        try {
            String tradeXml = JdPayUtil.genReqXml(tradeRefundReqDto, priKey, deskey);
            System.out.println("tradeXml:" + tradeXml);
            String refundUrl = PropertyUtils.getProperty("wepay.server.refund.url");

//              String resultJsonData = getJdRefundInfo(refundUrl, tradeXml);
            String resultJsonData = HttpsClientUtil.sendRequest(refundUrl, tradeXml, "application/xml");

            System.out.println("resultJsonData:" + resultJsonData);

            XMLToMap x= new XMLToMap();
            Map<String, String> map = x.getXML(resultJsonData);
            System.out.println("refundResponse:" + map);

            String status = map.get("code");
            if (status.equals("000000")){

                return true;
            }else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
        model.setRefundAmount(refund_amount);
        model.setRefundReason(refund_reason);
        model.setOutRequestNo("PYGH01RF001");
        alipay_request.setBizModel(model);

        AlipayTradeRefundResponse alipay_response;
        try {
            alipay_response = client.execute(alipay_request);
            System.out.println("alipay_response="+alipay_response);
            String result = alipay_response.getBody();
            JSONObject tmp = JSONObject.fromObject(result);
            String data = tmp.getString("alipay_trade_refund_response");
            System.out.println(data);
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
        }
    }

    //post请求方式访问数据
    private String getJdRefundInfo(String url,String content){
        URL httpUrl;
        String s = null;
        try {
            httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("contentType","application/xml");

            // 获取URLConnection对象对应的输出流
            OutputStream out = conn.getOutputStream();
            // 发送请求参数
            out.write(content.getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while((str = reader.readLine())!=null){
                sb.append(str);
            }
            s = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    //京东支付请求方法
    public Status JdPayReq(String tradeNum,String amount,String userid,String tradeName){
        JdOrderPay jdOrderPay = new JdOrderPay();
        jdOrderPay.setVersion("V2.0");
        jdOrderPay.setMerchant("110406033002");
        jdOrderPay.setTradeNum(tradeNum);
        jdOrderPay.setTradeName(tradeName);
        jdOrderPay.setTradeTime(formatterJ.format(new Date()));
        jdOrderPay.setAmount(amount);
        jdOrderPay.setOrderType("1");
        jdOrderPay.setCurrency("CNY");
        jdOrderPay.setCallbackUrl("http://gzpt.bjpygh.com/payResult.html"+"?ordernumber="+tradeNum);
        jdOrderPay.setNotifyUrl("http://gzpt.bjpygh.com/jNotify_url");//http://gzpt.bjpygh.com/jNotify_url
        jdOrderPay.setUserId(userid);

        String priKey = PropertyUtils.getProperty("wepay.merchant.rsaPrivateKey");
        String desKey = PropertyUtils.getProperty("wepay.merchant.desKey");
        System.out.println("priKey="+priKey+"   desKey="+desKey);
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
}
