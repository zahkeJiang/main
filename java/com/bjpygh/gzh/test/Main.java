package com.bjpygh.gzh.test;

import com.bjpygh.gzh.bean.UserOrder;
import com.bjpygh.gzh.model.AsynNotifyResponse;
import com.bjpygh.gzh.model.RefundResponse;
import com.bjpygh.gzh.utils.PropertyUtils;
import com.bjpygh.gzh.utils.ThreeDES;
import com.jd.jr.pay.gate.signature.util.JdPayUtil;


public class Main {
    public static void main(String[] args){
//        Date date = new Date(2017,8,29);
//        System.out.println(date.getDay());
//-----------------------------------------------------------------
//        List<UserOrder> orders = new ArrayList<UserOrder>();
//        UserOrder userOrder = new UserOrder();
//        userOrder.setOrderNumber("001");
//        userOrder.setOrderTime("2017-09-01");
//        orders.add(userOrder);
//
//        UserOrder userOrder3 = new UserOrder();
//        userOrder3.setOrderNumber("004");
//        userOrder3.setOrderTime("2017-09-04");
//        orders.add(userOrder3);
//
//        UserOrder userOrder1 = new UserOrder();
//        userOrder1.setOrderNumber("002");
//        userOrder1.setOrderTime("2017-09-02");
//        orders.add(userOrder1);
//
//        UserOrder userOrder2 = new UserOrder();
//        userOrder2.setOrderNumber("003");
//        userOrder2.setOrderTime("2017-09-03");
//        orders.add(userOrder2);
//
//        Collections.sort(orders,new Comparator<UserOrder>(){
//            public int compare(UserOrder arg0, UserOrder arg1) {
//                return arg0.getOrderTime().compareTo(arg1.getOrderTime());
//            }
//        });
//        Collections.reverse(orders);
//        for(UserOrder u : orders){
//            System.out.println(u.getOrderNumber());
//        }
//------------------------------------------------------------------------------
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date date1 = sdf.parse("2017-09-01");
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        //-------------------------------------------------------------------------------

//        ThreeDES threeDES = new ThreeDES();
//        threeDES.encryptDESCBC();
        String deskey = PropertyUtils.getProperty("wepay.merchant.desKey");
        String priKey = PropertyUtils.getProperty("wepay.merchant.rsaPrivateKey");
        String pubKey = PropertyUtils.getProperty("wepay.jd.rsaPublicKey");
        String resultJsonData = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><jdpay>  <version>V2.0</version>  <merchant>110406033002</merchant>  <result>    <code>000000</code>    <desc>success</desc>  </result>  <encrypt>MzVlZTgwNDY0YzJhNzZiZjIyZWQ4NmM3NWFmMDNiODJhZDBlMWM3YTIyYWIwZjQzMDVjMzdiZjI4MTRjMmVjYzcwOTNmZWI2NjIxNWFkODFlOTk0MWRmZWU5OWQ3N2E1YTEzNmQwZDA2YzZlMWVhYTgxZjk0OTdhMTMxMmQwNzkwZjBkMzhlMDFlZTJhYTZhZmRlNWRlYTM1ZjVmZTY1OTdlOGUyOGVkNWYyMTAzZGFiZmIwYzhiZTIzY2Y0ZDM4MWYzYjZhMDQ3NTgxNzQ3YTIxZWU3YzMxNTNmYWIyZTFmM2I3Nzc2MWJhOWM1NDY3ZTg1NTM4YjlmZTgwY2Q4Zjc5MGQxYjIwZDM5ODQ4OGFiZTA4OWMxNTg2YTY0MzJkMjhiYmEwYTVlMGEyYjI3YzY0ODg0MjZhYmVlNmJjNmQ0NWQyOWM3NTNjZTY4OTRkYzA2MTIwYzUwMTljNjA0ZGQzNWRhZjIwNzI2ZWI0OTg5YzQyNjliMDNjY2YxZjE2ZDcyNWJlOWMzMGEwMTM0MDFlMjA2MWJkOWYzMzk4NjE3OTlmMTM1MDAwMTdkMjYxYmJlODlhYWQ1YTc4YTA4NTdiMGJiODE0Yjg5ZjYxMWZmOTI3YjY3YTlmZmM0OTU5MGZhNDk1OThhYTBhYWFiODQ3YTAyNWIyYzM0N2E5YzcxMDAwZjE2NjY4ODE3NGQwMmI0MzY0MTc0ODIwYWE5YjJiYjA4NGYxMTdjMjdhN2ViMjNhZGZhNDZmOGFmMTBjN2Q2YTM2MzBkZDY1NjRlY2RhNjhiMzljNjliYTUxYTI0YWZmMTMzYTBlNWNhZDQwYWZiMDE1NjM2ZjdmNDBhMzM0NDVlYWQ0NDE2MTNiN2FmMGFiYTdmM2Y1ZjhhMGJmZWE3NWYwZmUyOTZmNjlmOGM4ODAxZDhmNzIyYTQzNDNiNjU0YTBkZTljYWMzODE3ZDI5NjFhMmZhNjMxOWIzYmVjZWNmNWVmMDNhODBmZDVkODAyMTE1ZWYxZWZkNjY0NjQxNjdmYWZlZWFiOTg1ZWNkZTRkMjhjNWVlYjZhMTliZmEzNzk4NWRkNzAyYzBlZDk1YjhhNjFmYmFiNmQ1ZTJkNjllNjlkNWVmN2YwNjMwZTQ3MTVjYWVkYzA5MDIyMzJkMDhlZGU3MmU5YjVkZmY4NzI1NWMxZDQ4YWJiOGIyNzA3NjlmNTljMDM3M2UyNzBhMjEwZTMwODY2MWJlZGFjN2NkMzgwZjA5YmIxZTk2NjVkMzMwZmU4N2ZjNzE1YzNkMWQ4YzZhMTFjMjRjYzJkNjllNjlkNWVmN2YwNjMwZTQ3MTVjYWVkYzA5MDIyYmM4MTAwY2NmM2U2YjZmNGM5OTdlNmMxNDAyZjFkM2MyZDg3ZDIzN2QyNmQyOTk4ZDdiZmFmODRlYjAxY2NiYjMzM2EzMjMyYTg5N2IwZDhmZjA1Mjc0NGUzZWE2ZjBkNzU5OGZhMzRiNzJiNDg2NmYzODkzM2Y0NjIyM2I0N2E5Zjc4NjZhZDk0YTc0ZjY4NmQ3OTFlOTMzNWNiZTQxYmUzZDcwNDg5MDM1MzA2Zjk=</encrypt></jdpay>";
        try {
            AsynNotifyResponse anRes = JdPayUtil.parseResp(pubKey, deskey, resultJsonData, AsynNotifyResponse.class);
            System.out.println("异步通知解析数据:" + anRes);
            System.out.println("异步通知订单号：" + anRes.getTradeNum() + ",状态：" + anRes.getStatus() + "成功!!!!");

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
//29 6 5
//28 5 4
//27 4 3
//26 3 2
//25 2 1
//24 1 7
//23 0 6