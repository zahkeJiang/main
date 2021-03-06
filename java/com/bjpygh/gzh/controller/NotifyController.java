package com.bjpygh.gzh.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.bjpygh.gzh.bean.*;
import com.bjpygh.gzh.config.AlipayConfig;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.model.AsynNotifyResponse;
import com.bjpygh.gzh.service.*;
import com.bjpygh.gzh.utils.OrderPush;
import com.bjpygh.gzh.utils.PropertyUtils;
import com.bjpygh.gzh.utils.XMLToMap;
import com.jd.jr.pay.gate.signature.util.JdPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class NotifyController extends BaseController {


    @Autowired
    UserService userService;

    @Autowired
    RecordService recordService;

    @Autowired
    DsOrderService dsOrderService;

    @Autowired
    CouponService couponService;

    @Autowired
    VillaOrderService villaOrderService;

    @Autowired
    ArmyOrderService armyOrderService;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //微信支付回调接口
    @RequestMapping(value = "/notify.action", method = RequestMethod.POST)
    public void WxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String inputLine;
        String notityXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            request.getReader().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        XMLToMap x= new XMLToMap();
        Map<String, String> map = x.getXML(notityXml);
        System.out.println(map);
        if(map.get("result_code").equals("SUCCESS")){
            String orderNumber = map.get("out_trade_no");
            String o = orderNumber.substring(0, 1);
            if (o.equals("A")){
                ArmyOrder armyOrder = armyOrderService.getArmyOrderByNumber(map.get("out_trade_no")).get(0);
                armyOrder.setOrderStatus(1);
                armyOrder.setPayType((byte) 1);
                armyOrder.setPayTime(formatter.format(new Date()));
                armyOrderService.updateOrder(armyOrder);

                pushToWangNan(armyOrder.getOrderNumber(),armyOrder.getArmyPrice(),"o9C-m0ha_E3iP5KkgLEmsREff9d0");

                User userById = userService.getUserById(String.valueOf(armyOrder.getUserId()));
                ArmyMessagePush(armyOrder,userById.getOpenid());
            }if (o.equals("V")){
                VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(map.get("out_trade_no")).get(0);
                villaOrder.setOrderStatus(1);
                villaOrder.setPayType((byte) 1);
                villaOrder.setPayTime(formatter.format(new Date()));
                villaOrderService.updateOrder(villaOrder);

                //推送给张智良
                pushToWangNan(villaOrder.getOrderNumber(),villaOrder.getVillaPrice(),"o9C-m0m1ywvetapVQsq_fpoPPJOQ");
                //推送给蒋圆
                pushToWangNan(villaOrder.getOrderNumber(),villaOrder.getVillaPrice(),"o9C-m0gWfR9WOs8DIDElxSUfDIUU");

                User userById = userService.getUserById(String.valueOf(villaOrder.getUserId()));
                VillaMessagePush(villaOrder,userById.getOpenid());
            }else {
                System.out.println("result_code:fail");
            }
        }else{
            System.out.println("result_code:fail");
        }
        response.getWriter().print("<xml>  <return_code><![CDATA[SUCCESS]]></return_code>  <return_msg><![CDATA[OK]]></return_msg></xml>");

    }

    //支付宝驾校支付回调接口
    @RequestMapping(value = "/notify_url", method = RequestMethod.POST)
    public void AliNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {

        PrintWriter out = response.getWriter();

        Map<String, String> map = getGmtRefund(request);
        if (map.get("boolean").equals("true")){

//            DsOrder dsOrder = dsOrderService.getDsOrderByNumber(map.get("out_trade_no")).get(0);
//            dsOrder.setOrderStatus((byte) 1);
//            dsOrder.setPayType((byte) 0);
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            dsOrder.setPayTime(formatter.format(new Date()));
            DsOrder dsOrder = dsOrderService.getDsOrderByNumber(map.get("ordernumber")).get(0);
            dsOrder.setOrderStatus((byte) 1);
            dsOrder.setPayType((byte) 0);
            dsOrder.setPayTime(formatter.format(new Date()));
            dsOrderService.updateOrder(dsOrder);

            //推送给张智良
            pushToWangNan(dsOrder.getOrderNumber(),dsOrder.getOrderPrice(),"o9C-m0m1ywvetapVQsq_fpoPPJOQ");
            //推送给蒋圆
            pushToWangNan(dsOrder.getOrderNumber(),dsOrder.getOrderPrice(),"o9C-m0gWfR9WOs8DIDElxSUfDIUU");

            out.println("success");	//请不要修改或删除
            User userById = userService.getUserById(String.valueOf(dsOrder.getUserId()));
            DsMessagePush(dsOrder,userById.getOpenid());
        } else{//验证失败
            out.println("fail");
        }

        out.flush();
        out.close();
    }

    //给王男推送
    private void pushToWangNan(String ordernumber,int price, String openid) {
        OrderPush orderPush = new OrderPush();
        Map<String, String> map = new HashMap<String, String>();
        map.put("first","有人报名了！！！ \n");
        map.put("keyword1",price+"元");
        map.put("keyword2",ordernumber+"\n");
        map.put("remark","赶紧去服务");
        map.put("openid",openid);

        orderPush.PayJsonObj(map);
    }
    //驾校订单支付微信消息推送
    private void DsMessagePush(DsOrder dsOrder, String openid) {
        OrderPush orderPush = new OrderPush();
        Map<String, String> map = new HashMap<String, String>();
        map.put("first","我们已收到您的报名费用，报名成功后小漂会及时给您反馈，请耐心等待。\n");
        map.put("keyword1",dsOrder.getOrderPrice()+"元");
        map.put("keyword2",dsOrder.getOrderNumber()+"\n");
        map.put("remark","如有问题咨询客服：010-59822296或直接在微信留言，小漂将第一时间为您服务！");
        map.put("openid",openid);

        orderPush.PayJsonObj(map);
    }

    //别墅订单支付微信消息推送
    private void VillaMessagePush(VillaOrder villaOrder, String openid) {
        OrderPush orderPush = new OrderPush();
        Map<String, String> map = new HashMap<String, String>();
        map.put("first","您已完成“漂洋过海小别墅”订单支付\n");
        map.put("keyword1",villaOrder.getVillaPrice()+"元");
        map.put("keyword2",villaOrder.getOriginalPrice()-villaOrder.getVillaPrice()+"元");
        map.put("keyword3",villaOrder.getOrderNumber()+"\n");
        map.put("remark","漂洋过海小别墅期待您的入住。");
        map.put("openid",openid);

        orderPush.PayJsonObj(map);
    }

    //军旅订单支付微信消息推送
    private void ArmyMessagePush(ArmyOrder armyOrder, String openid) {
        OrderPush orderPush = new OrderPush();
        Map<String, String> map = new HashMap<String, String>();
        map.put("first","您已完成“作战之日”订单支付\n");
        map.put("keyword1",armyOrder.getArmyPrice()+"元");
        map.put("keyword2",armyOrder.getOriginalPrice()-armyOrder.getArmyPrice()+"元");
        map.put("keyword3",armyOrder.getOrderNumber()+"\n");
        map.put("remark","小漂已经迫不及待想和你一起突突突啦。");
        map.put("openid",openid);

        orderPush.PayJsonObj(map);
    }


    //支付宝别墅支付回调接口
    @RequestMapping(value = "/vnotify_url", method = RequestMethod.POST)
    public void AliVillaNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        Map<String, String> userMap = checkWxUser(request);
        PrintWriter out = response.getWriter();

        Map<String, String> map = getGmtRefund(request);
        System.out.println("boolean+"+map.get("boolean"));
        if (map.get("boolean").equals("true")){
            out.println("success");	//请不要修改或删除
            VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(map.get("ordernumber")).get(0);
            villaOrder.setOrderStatus(1);
            villaOrder.setPayType((byte) 0);
            villaOrder.setPayTime(formatter.format(new Date()));
            villaOrderService.updateOrder(villaOrder);

            //推送给张智良
            pushToWangNan(villaOrder.getOrderNumber(),villaOrder.getVillaPrice(),"o9C-m0m1ywvetapVQsq_fpoPPJOQ");
            //推送给蒋圆
            pushToWangNan(villaOrder.getOrderNumber(),villaOrder.getVillaPrice(),"o9C-m0gWfR9WOs8DIDElxSUfDIUU");

            User userById = userService.getUserById(String.valueOf(villaOrder.getUserId()));
            VillaMessagePush(villaOrder,userById.getOpenid());
        }else{//验证失败
            out.println("fail");
        }
        out.flush();
        out.close();
    }


    //支付宝军旅支付回调接口
    @RequestMapping(value = "/anotify_url", method = RequestMethod.POST)
    public void AliArmyNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        PrintWriter out = response.getWriter();

        Map<String, String> map = getGmtRefund(request);
        if (map.get("boolean").equals("true")){
            out.println("success");	//请不要修改或删除
            ArmyOrder armyOrder = armyOrderService.getArmyOrderByNumber(map.get("ordernumber")).get(0);
            armyOrder.setOrderStatus(1);
            armyOrder.setPayType((byte) 0);
            armyOrder.setPayTime(formatter.format(new Date()));
            armyOrderService.updateOrder(armyOrder);

            //推送给张智良
            pushToWangNan(armyOrder.getOrderNumber(),armyOrder.getArmyPrice(),"o9C-m0m1ywvetapVQsq_fpoPPJOQ");
            //推送给蒋圆
            pushToWangNan(armyOrder.getOrderNumber(),armyOrder.getArmyPrice(),"o9C-m0gWfR9WOs8DIDElxSUfDIUU");

            User userById = userService.getUserById(String.valueOf(armyOrder.getUserId()));
            ArmyMessagePush(armyOrder,userById.getOpenid());
        }else{//验证失败
            out.println("fail");
        }

        out.flush();
        out.close();
    }

    //京东驾校支付回调接口
    @ResponseBody
    @RequestMapping(value = "/jNotify_url")
    public String execute(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader( request.getInputStream()));
            String line = null;

            while ((line = br.readLine()) != null) {

                sb.append(line);
            }
        } catch (IOException e) {
            System.out.println(e);
            return "fail";
        }
        System.out.println("xml"+sb.toString());
        String deskey = PropertyUtils.getProperty("wepay.merchant.desKey");
        String pubKey = PropertyUtils.getProperty("wepay.jd.rsaPublicKey");
        try {
            AsynNotifyResponse anRes = JdPayUtil.parseResp(pubKey, deskey, sb.toString(), AsynNotifyResponse.class);

            if (anRes.getStatus().equals("2")){
                String ordernumber = anRes.getTradeNum();
                String o = ordernumber.substring(0, 1);
                if (o.equals("V")){
                    VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(ordernumber).get(0);
                    villaOrder.setOrderStatus(1);
                    villaOrder.setPayType((byte) 3);
                    villaOrder.setPayTime(formatter.format(new Date()));
                    villaOrderService.updateOrder(villaOrder);
                    //推送给张智良
                    pushToWangNan(villaOrder.getOrderNumber(),villaOrder.getVillaPrice(),"o9C-m0m1ywvetapVQsq_fpoPPJOQ");
                    //推送给蒋圆
                    pushToWangNan(villaOrder.getOrderNumber(),villaOrder.getVillaPrice(),"o9C-m0gWfR9WOs8DIDElxSUfDIUU");

                    User userById = userService.getUserById(String.valueOf(villaOrder.getUserId()));
                    VillaMessagePush(villaOrder,userById.getOpenid());
                }else if (o.equals("D")){
                    DsOrder dsOrder = dsOrderService.getDsOrderByNumber(ordernumber).get(0);
                    dsOrder.setOrderStatus((byte) 1);
                    dsOrder.setPayType((byte) 3);
                    dsOrder.setPayTime(formatter.format(new Date()));
                    dsOrderService.updateOrder(dsOrder);
                    //推送给张智良
                    pushToWangNan(dsOrder.getOrderNumber(),dsOrder.getOrderPrice(),"o9C-m0m1ywvetapVQsq_fpoPPJOQ");
                    //推送给蒋圆
                    pushToWangNan(dsOrder.getOrderNumber(),dsOrder.getOrderPrice(),"o9C-m0gWfR9WOs8DIDElxSUfDIUU");

                    DsMessagePush(dsOrder,"o9C-m0ha_E3iP5KkgLEmsREff9d0");

                    User userById = userService.getUserById(String.valueOf(dsOrder.getUserId()));

                    DsMessagePush(dsOrder,userById.getOpenid());

                }else if (o.equals("A")){
                    ArmyOrder armyOrder = armyOrderService.getArmyOrderByNumber(ordernumber).get(0);
                    armyOrder.setOrderStatus(1);
                    armyOrder.setPayType((byte) 3);
                    armyOrder.setPayTime(formatter.format(new Date()));
                    armyOrderService.updateOrder(armyOrder);

                    //推送给张智良
                    pushToWangNan(armyOrder.getOrderNumber(),armyOrder.getArmyPrice(),"o9C-m0m1ywvetapVQsq_fpoPPJOQ");
                    //推送给蒋圆
                    pushToWangNan(armyOrder.getOrderNumber(),armyOrder.getArmyPrice(),"o9C-m0gWfR9WOs8DIDElxSUfDIUU");

                    User userById = userService.getUserById(String.valueOf(armyOrder.getUserId()));
                    ArmyMessagePush(armyOrder,userById.getOpenid());
                }

            }
        } catch (Exception e) {
            System.out.println(e);
            return "fail";
        }
        return "ok";
    }

    //查询订单进度接口
    @ResponseBody
    @RequestMapping(value = "/schedule.action", method = RequestMethod.POST)
    public Status schedule(HttpServletRequest request, String ordernumber) throws ParseException {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");

        String o = ordernumber.substring(0,1);
        if (o.equals("V")){
            User user = userService.getUserById(userid);
            VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(ordernumber).get(0);
            UserOrder order = new UserOrder();
            order.setOrderPrice(villaOrder.getVillaPrice());
            order.setOrderStatus(villaOrder.getOrderStatus());
            order.setOrderImage(villaOrder.getImageurl());
            String[] vs = villaOrder.getVillaName().split(",");
            String vn = "";
            for (int i=0;i<vs.length;i++){
                vn += vs[i]+" ";
            }
            order.setOrderDescripe("别墅: "+vn+" / "+
                    "入住人数: "+villaOrder.getPeopleNumber()+"人 / "+"入住天数: "
                    +villaOrder.getDate().split(",").length+"晚");
            order.setOrderTime(villaOrder.getCreateTime());
            order.setOrderName(villaOrder.getNote());
            order.setOrderNumber(villaOrder.getOrderNumber());
            order.setPhoneNumber(user.getPhoneNumber());
            order.setRealName(villaOrder.getRealName());
            order.setOriginalPrice(villaOrder.getOriginalPrice());
            return Status.success().add("order",order).add("price","0");
        }else if (o.equals("D")){
            DsOrder dsOrder;
            List<DsOrder> dsOrders = dsOrderService.getDsOrderByNumber(ordernumber);
            if (!(dsOrders.size()>0)){
                return Status.fail(-20,"没有订单");
            }else{
                dsOrder = dsOrders.get(0);
            }
            UserCoupon dsCoupon = couponService.getDsCoupon(userid);
            Integer couponPrice = 0;
            if (dsCoupon != null ){
                Date couponTime = dsCoupon.getCouponTime();
                Date createTime = formatter.parse(dsOrder.getCreateTime());
                if (createTime.getTime()-couponTime.getTime()<2678400000L){
                    couponPrice = dsCoupon.getCouponPrice();
                }
            }
            UserOrder order = new UserOrder();
            order.setOrderPrice(dsOrder.getOrderPrice());
            order.setOrderStatus(dsOrder.getOrderStatus());
            order.setOrderImage(dsOrder.getImageurl());
            order.setOrderDescripe("班型: "+dsOrder.getDsType()+" / "
                    +"驾照类型: "+dsOrder.getModels()+" / "
                    +"学习类型: "+dsOrder.getTrainTime()+" / "
                    +"法培方式: "+dsOrder.getNote());
            order.setOrderTime(dsOrder.getCreateTime());
            order.setOrderName(dsOrder.getDsName());
            order.setOrderNumber(dsOrder.getOrderNumber());
            order.setPhoneNumber(dsOrder.getPhoneNumber());
            order.setRealName(dsOrder.getRealName());
            order.setOriginalPrice(dsOrder.getOriginalPrice());
            order.setDescription(dsOrder.getDescription());
            order.setDsNote("法培方式"+dsOrder.getNote());
            return Status.success().add("order",order)
                    .add("price",couponPrice);
        }else if (o.equals("A")){
            ArmyOrder armyOrder = armyOrderService.getArmyOrderByNumber(ordernumber).get(0);
            User user = userService.getUserById(userid);
            UserOrder order = new UserOrder();
            order.setOrderPrice(armyOrder.getArmyPrice());
            order.setOrderStatus(armyOrder.getOrderStatus());
            order.setOrderImage(armyOrder.getImageurl());
            String period;
            if (armyOrder.getPeriod()==0){
                period ="时间段: 8:00-11:30";
            }else {
                period = "时间段： 13:30-17:00";
            }
            order.setOrderDescripe("参与人数: "+armyOrder.getPeopleNumber()+"人 / "
                    +"参与天数: "+armyOrder.getDate().split(",").length+"天 / "+period);
            order.setOrderTime(armyOrder.getCreateTime());
            order.setOrderName(armyOrder.getArmyName());
            order.setOrderNumber(armyOrder.getOrderNumber());
            order.setPhoneNumber(user.getPhoneNumber());
            order.setRealName(armyOrder.getRealName());
            order.setOriginalPrice(armyOrder.getOriginalPrice());
            return Status.success().add("order",order).add("price","0");
        }else {
            return Status.fail(-20,"处理失败");
        }

    }

    //查询订单接口及所属驾校
    @ResponseBody
    @RequestMapping(value = "/queryOrder.action", method = RequestMethod.POST)
    public Status QueryOrder(HttpServletRequest request,String ordernumber){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");

        String o = ordernumber.substring(0,1);
        if (o.equals("A")){
            ArmyOrder armyOrder = armyOrderService.getArmyOrderByNumber(ordernumber).get(0);
            if (armyOrder.getOrderStatus() == 1){
                return Status.success().add("price",armyOrder.getArmyPrice())
                        .add("out_trade_no",armyOrder.getOrderNumber());
            }else {
                return Status.fail(-30,"没有已支付订单");
            }
        }else if (o.equals("D")){
            DsOrder dso = dsOrderService.getDsOrderByNumber(ordernumber).get(0);
            if (dso.getOrderStatus() == 1){
                return Status.success().add("price",dso.getOrderPrice())
                        .add("out_trade_no",dso.getOrderNumber());
            }else {
                return Status.fail(-30,"没有已支付订单");
            }
        }else if (o.equals("V")){
            VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(ordernumber).get(0);
            if (villaOrder.getOrderStatus() == 1){
                return Status.success().add("price",villaOrder.getVillaPrice())
                        .add("out_trade_no",villaOrder.getOrderNumber());
            }else {
                return Status.fail(-30,"没有已支付订单");
            }
        }else {
            return Status.fail(-30,"没有已支付订单");
        }
    }

    public Map<String, String> getGmtRefund(HttpServletRequest request) throws IOException, AlipayApiException {

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        System.out.print("params="+params.toString());
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
        Map<String,String> map = new HashMap<String, String>();
        map.put("out_trade_no",out_trade_no);
        if(verify_result){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码



            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序
                if(request.getParameter("gmt_refund")==null){

                    map.put("boolean","true");
                    map.put("ordernumber",out_trade_no);
                    return map;
                }else {
                    map.put("boolean","false");
                    return map;
                }

                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            }

            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
//            out.clear();

            //out.flush();
            //out.close();
            //////////////////////////////////////////////////////////////////////////////////////////
        }
        map.put("boolean","false");
        return map;
    }

}
