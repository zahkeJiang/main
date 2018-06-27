package com.bjpygh.gzh.test;

import com.bjpygh.gzh.bean.UserOrder;
import com.bjpygh.gzh.model.AsynNotifyResponse;
import com.bjpygh.gzh.model.RefundResponse;
import com.bjpygh.gzh.utils.Http;
import com.bjpygh.gzh.utils.OrderPush;
import com.bjpygh.gzh.utils.PropertyUtils;
import com.bjpygh.gzh.utils.ThreeDES;
import net.sf.json.JSON;
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
//            Date date1 = sdf.parse("2017-09-01");
//            Date date2 = sdf.parse("2017-09-16");
//
//            System.out.println(date1.getDate());
//            System.out.println(date2.getDate());
//            System.out.println(date2.getTime()-date1.getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
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


//        System.out.println(Pattern.matches(".*速*.","速成快板"));

//        System.out.println(Math.abs(-1));

        JSONObject a = new JSONObject();
        a.element("title", "欢乐谷端午门票只需99元另外赠送大礼！！！");
        a.element("description", "欢乐谷第四期甜品王国全面上线，13大项目酷爽嗨萌！家庭过山车、蛋糕秋千、尖峰时刻、甜心飞饼、飞跃牛奶河、皇宫影院......");
        a.element("url","https://mp.weixin.qq.com/s/QcEkwJ9tef-FmxdCT-ZSHQ");
        a.element("picurl", "https://mmbiz.qlogo.cn/mmbiz_jpg/G9kiclPrCRqJBtjReDEXpMEYSteh61oEo0oLQ9DAPibcicRxpWtIiapNYkRm1WicDxib1anicshlL9ODWAiaLfUMoZkJ0w/0?wx_fmt=jpeg");
        JSONArray articles = new JSONArray();
        articles.element(a);
        JSONObject obj = new JSONObject();
        obj.accumulate("touser","qrehwahadfhadhasfhasdfh");
        obj.accumulate("msgtype","news");
        obj.accumulate("news",articles);

        System.out.println(obj);

    }


//29 6 5
//28 5 4
//27 4 3
//26 3 2
//25 2 1
//24 1 7
//23 0 6

}