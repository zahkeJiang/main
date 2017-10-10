package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.ArmyOrder;
import com.bjpygh.gzh.bean.VillaOrder;
import com.bjpygh.gzh.bean.VillaPrice;
import com.bjpygh.gzh.dao.ArmyOrderMapper;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.ArmyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class ArmyOrderController extends BaseController {

    @Autowired
    ArmyOrderService armyOrderService;

    //创建军旅订单接口
    @ResponseBody
    @RequestMapping(value = "/createArmyOrder", method = RequestMethod.POST)
    public Status createArmyOrder(HttpServletRequest request, ArmyOrder armyOrder){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        armyOrder.setUserId(Long.valueOf(userid));
        String orderNumber = armyOrderService.createArmyOrder(armyOrder);
        return Status.success().add("orderNumber",orderNumber);
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

}
