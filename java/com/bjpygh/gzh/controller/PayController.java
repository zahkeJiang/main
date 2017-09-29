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
import com.bjpygh.gzh.entity.DsAliPay;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.*;
import com.bjpygh.gzh.utils.MD5;
import com.github.wxpay.sdk.WXPay;
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

    @ResponseBody
    @RequestMapping(value = "/refund.action", method = RequestMethod.POST)
    public Status getUserId(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }

//			String userid = userMap.get("id");

        String out_trade_no = new String(request.getParameter("ordernumber").getBytes("ISO-8859-1"),"UTF-8");



        String refund_reason=new String(request.getParameter("WIDrefund_reason").getBytes("ISO-8859-1"),"UTF-8");

        DsOrder dsOrder = dsOrderService.getDsOrderByNumber(out_trade_no).get(0);

        if(dsOrder.getOrderStatus() == 3){
            return Status.fail(-30,"报名已完成");
        }
        String refund_amount=""+dsOrder.getOrderPrice();
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
                    //改变订单状态
                    dsOrder.setOrderStatus((byte) 5);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    dsOrder.setRefundTime(formatter.format(new Date()));
                    dsOrderService.updateOrder(dsOrder);
                    return Status.success();
                }else{
                    return Status.fail(-20,result);
                }
            }else{
                return Status.fail(-20,result);
            }

        } catch (AlipayApiException e) {
            return Status.fail(-20,"处理失败");
        }finally{
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

}
