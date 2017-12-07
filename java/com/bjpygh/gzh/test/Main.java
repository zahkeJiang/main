package com.bjpygh.gzh.test;

import com.bjpygh.gzh.bean.UserOrder;
import com.bjpygh.gzh.model.AsynNotifyResponse;
import com.bjpygh.gzh.model.RefundResponse;
import com.bjpygh.gzh.utils.OrderPush;
import com.bjpygh.gzh.utils.PropertyUtils;
import com.bjpygh.gzh.utils.ThreeDES;
import com.jd.jr.pay.gate.signature.util.JdPayUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {
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
//            Date date1 = sdf.parse("2017-10-1");
//            Date date2 = sdf.parse("2017-11-01");

//            System.out.println(date2);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        int a = 1887;
//        System.out.println(""+(a/2));
        //-------------------------------------------------------------------------------

//        ThreeDES threeDES = new ThreeDES();
//        threeDES.encryptDESCBC();


//        System.out.println(1*0.5);

//        OrderPush orderPush = new OrderPush();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("first","Hi，您已成功提交驾考订单");
//        map.put("orderID","DSPYGH20178212216292");
//        map.put("orderMoneySum",1280+"");
//        map.put("remark","请尽快支付，如有问题咨询客服：010-59822296");
//        map.put("openid","o9C-m0gWfR9WOs8DIDElxSUfDIUU");
//
//        System.out.println(orderPush.CreateJsonObj(map));


        System.out.println(Pattern.matches(".*速*.","速成快板"));
    }

//29 6 5
//28 5 4
//27 4 3
//26 3 2
//25 2 1
//24 1 7
//23 0 6

}