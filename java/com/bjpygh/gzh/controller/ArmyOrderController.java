package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.ArmyOrder;
import com.bjpygh.gzh.bean.DsOrder;
import com.bjpygh.gzh.bean.VillaOrder;
import com.bjpygh.gzh.bean.VillaPrice;
import com.bjpygh.gzh.dao.ArmyOrderMapper;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.ArmyOrderService;
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
public class ArmyOrderController extends BaseController {

    @Autowired
    ArmyOrderService armyOrderService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //创建军旅订单接口
    @ResponseBody
    @RequestMapping(value = "/createArmyOrder", method = RequestMethod.POST)
    public Status createArmyOrder(HttpServletRequest request, ArmyOrder armyOrder) throws ParseException {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");

        List<ArmyOrder> dsos = new ArrayList<ArmyOrder>();
        List<ArmyOrder> list = armyOrderService.getOrdersById(userid);
        for (ArmyOrder dso : list){
            if (dso.getOrderStatus() == 0){
                dsos.add(dso);
            }
        }
        if (dsos.size()>2){
            return Status.fail(-40,"您已创建三个订单，无法创建更多订单");
        }

        armyOrder.setUserId(Long.valueOf(userid));
        ArmyOrder armyOrder1 = armyOrderService.createArmyOrder(armyOrder);
        OrderPush orderPush = new OrderPush();
        Map<String, String> map = new HashMap<String, String>();
        map.put("first","Hi,您已成功提交“作战之日”订单");
        map.put("orderID",armyOrder1.getOrderNumber());
        map.put("orderMoneySum",armyOrder1.getArmyPrice()+"元");
        map.put("remark","请尽快支付，如有问题咨询客服：010-59822296");
        map.put("openid",userMap.get("openid"));

        orderPush.CreateJsonObj(map);
        return Status.success().add("orderNumber",armyOrder1.getOrderNumber());
    }

    //删除军旅订单接口
    @ResponseBody
    @RequestMapping(value = "/cancelArmyOrder", method = RequestMethod.POST)
    public Status cancelOrder(HttpServletRequest request,String ordernumber){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        armyOrderService.updateOrderStatus(ordernumber);
        return Status.success();
    }

    //获取军旅订单接口
    @ResponseBody
    @RequestMapping(value = "/getArmyOrder", method = RequestMethod.POST)
    public Status getOrder(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        List<ArmyOrder> armyOrders = armyOrderService.getArmyOrder(userid);
        if (armyOrders.size()>0){
            return Status.success().add("armyOrders",armyOrders);
        }else{
            return Status.fail(-20,"没有订单");
        }

    }

    //获取订单价格
    @ResponseBody
    @RequestMapping(value = "/getArmyPrice", method = RequestMethod.POST)
    public Status getArmyPrice(HttpServletRequest request,String date) throws ParseException {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        Date date1 = sdf.parse(date);
        List<VillaPrice> priceList = armyOrderService.getPriceList(date1);
        return Status.success().add("priceList",priceList);
    }

}
