package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.VillaOrder;
import com.bjpygh.gzh.bean.VillaPrice;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.VillaOrderService;
import com.bjpygh.gzh.utils.OrderPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class VillaOrderController extends BaseController{

    @Autowired
    VillaOrderService villaOrderService;

    //创建订单接口
    @ResponseBody
    @RequestMapping(value = "/createVillaOrder", method = RequestMethod.POST)
    public Status createOrder(HttpServletRequest request, VillaOrder villaOrder) throws ParseException {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");

        List<VillaOrder> dsos = new ArrayList<VillaOrder>();
        List<VillaOrder> list = villaOrderService.getOrdersById(userid);
        for (VillaOrder dso : list){
            if (dso.getOrderStatus() == 0){
                dsos.add(dso);
            }
        }
        if (dsos.size()>2){
            return Status.fail(-40,"您已创建三个订单，无法创建更多订单");
        }

        villaOrder.setUserId(Long.valueOf(userid));
        VillaOrder villaOrder1 = villaOrderService.createVillaOrder(villaOrder);

        OrderPush orderPush = new OrderPush();
        Map<String, String> map = new HashMap<String, String>();
        map.put("first","Hi您已成功提交“漂洋过海”小别墅订单");
        map.put("orderID",villaOrder1.getOrderNumber());
        map.put("orderMoneySum",villaOrder1.getVillaPrice()+"元");
        map.put("remark","请尽快支付，如有问题咨询客服：010-59822296");
        map.put("openid",userMap.get("openid"));

        orderPush.CreateJsonObj(map);
        return Status.success().add("orderNumber",villaOrder1.getOrderNumber());
    }

    //关闭订单接口
    @ResponseBody
    @RequestMapping(value = "/cancelVillaOrder", method = RequestMethod.POST)
    public Status cancelOrder(HttpServletRequest request,String ordernumber){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        villaOrderService.updateOrderStatus(ordernumber);
        return Status.success();
    }

    //获取订单接口
    @ResponseBody
    @RequestMapping(value = "/getVillaOrder", method = RequestMethod.POST)
    public Status getOrder(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        List<VillaOrder> villaOrder = villaOrderService.getVillaOrder(userid);
        if (villaOrder.size()>0){
            return Status.success().add("villaOrders",villaOrder);
        }else{
            return Status.fail(-20,"没有订单");
        }

    }

    //获取订单价格
    @ResponseBody
    @RequestMapping(value = "/getVillaPrice", method = RequestMethod.POST)
    public Status getVillaPrice(HttpServletRequest request,String date) throws ParseException {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(date);
        List<VillaPrice> priceList = villaOrderService.getPriceList(date1);
        return Status.success().add("priceList",priceList);
    }

    //获取已报名别墅
    @ResponseBody
    @RequestMapping(value = "/getVillaRes", method = RequestMethod.POST)
    public Status getVillaPay(HttpServletRequest request,String[] date) throws ParseException {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String selected = villaOrderService.getVillaPay(date);
        return Status.success().add("selected",selected);
    }

    @RequestMapping(value = "/tuanjian.action", method = RequestMethod.GET)
    public String toPersonal(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return "error";
        }
        return "villa_and_army";
    }

}

