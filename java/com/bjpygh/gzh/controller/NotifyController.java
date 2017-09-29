package com.bjpygh.gzh.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.bjpygh.gzh.bean.DsOrder;
import com.bjpygh.gzh.bean.IntegralRecord;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.bean.VillaOrder;
import com.bjpygh.gzh.config.AlipayConfig;
import com.bjpygh.gzh.service.DsOrderService;
import com.bjpygh.gzh.service.RecordService;
import com.bjpygh.gzh.service.UserService;
import com.bjpygh.gzh.service.VillaOrderService;
import com.bjpygh.gzh.utils.XMLToMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class NotifyController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    RecordService recordService;

    @Autowired
    DsOrderService dsOrderService;

    @Autowired
    VillaOrderService villaOrderService;

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
            String total_fee = map.get("total_fee");
            User user = userService.getUserByOpenid(map.get("openid")).get(0);
            Map<String, String> uMap = new HashMap<String, String>();

            //插入充值记录
            IntegralRecord record = new IntegralRecord();

            uMap.put("userid", ""+user.getUserId());
            if (total_fee.equals("500")) {
                uMap.put("memberPoints", (user.getMemberPoints() + 5) + "");
                uMap.put("integral", (user.getIntegral() + 5) + "");
                userService.changeUserPoints(uMap);
                record.setValue("+5");

            } else if (total_fee.equals("1000")) {
                uMap.put("memberPoints", (user.getMemberPoints() + 10) + "");
                uMap.put("integral", (user.getIntegral() + 10) + "");
                userService.changeUserPoints(uMap);
                record.setValue("+10");

            } else if (total_fee.equals("1980")) {
                uMap.put("memberPoints", (user.getMemberPoints() + 20) + "");
                uMap.put("integral", (user.getIntegral() + 20) + "");
                userService.changeUserPoints(uMap);
                record.setValue("+20");

            } else if (total_fee.equals("4900")) {
                uMap.put("memberPoints", (user.getMemberPoints() + 50) + "");
                uMap.put("integral", (user.getIntegral() + 50) + "");
                userService.changeUserPoints(uMap);
                record.setValue("+50");

            } else if (total_fee.equals("9750")) {
                uMap.put("memberPoints", (user.getMemberPoints() + 100) + "");
                uMap.put("integral", (user.getIntegral() + 100) + "");
                userService.changeUserPoints(uMap);
                record.setValue("+100");

            } else if (total_fee.equals("19500")) {
                uMap.put("memberPoints", (user.getMemberPoints() + 200) + "");
                uMap.put("integral", (user.getIntegral() + 200) + "");
                userService.changeUserPoints(uMap);
                record.setValue("+200");

            } else {
            }

            record.setNote("微信支付充值");
            record.setUserId(user.getUserId());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            record.setTime(formatter.format(new Date()));
            recordService.insertRecord(record);

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
            out.println("success");	//请不要修改或删除
            DsOrder dsOrder = dsOrderService.getDsOrderByNumber(map.get("out_trade_no")).get(0);
            dsOrder.setOrderStatus((byte) 1);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dsOrder.setPayTime(formatter.format(new Date()));
            dsOrderService.updateOrder(dsOrder);
        } else{//验证失败
            out.println("fail");
        }
    }

    //支付宝别墅支付回调接口
    @RequestMapping(value = "/vnotify_url", method = RequestMethod.POST)
    public void AliVillaNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        PrintWriter out = response.getWriter();

        Map<String, String> map = getGmtRefund(request);
        if (map.get("boolean").equals("true")){
            out.println("success");	//请不要修改或删除
            villaOrderService.ChangeVillaStatusByNumber(map.get("out_trade_no"));
        }else{//验证失败
            out.println("fail");
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
