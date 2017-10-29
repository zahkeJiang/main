package com.bjpygh.gzh.test;

import com.bjpygh.gzh.bean.UserOrder;
import com.bjpygh.gzh.model.AsynNotifyResponse;
import com.bjpygh.gzh.model.RefundResponse;
import com.bjpygh.gzh.utils.PropertyUtils;
import com.bjpygh.gzh.utils.ThreeDES;
import com.jd.jr.pay.gate.signature.util.JdPayUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse("2017-10-22");
            Date date2 = sdf.parse("2017-10-29");

            System.out.println(date2.getTime()-date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        int a = 1887;
//        System.out.println(""+(a/2));
        //-------------------------------------------------------------------------------

//        ThreeDES threeDES = new ThreeDES();
//        threeDES.encryptDESCBC();
    }
}
//29 6 5
//28 5 4
//27 4 3
//26 3 2
//25 2 1
//24 1 7
//23 0 6