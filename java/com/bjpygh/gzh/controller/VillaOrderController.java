package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.VillaOrder;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.VillaOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class VillaOrderController extends BaseController{

    @Autowired
    VillaOrderService villaOrderService;

    //创建订单接口
    @ResponseBody
    @RequestMapping(value = "/createVillaOrder", method = RequestMethod.POST)
    public Status createOrder(HttpServletRequest request, VillaOrder villaOrder){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        villaOrder.setUserId(Long.valueOf(userid));
        String orderNumber = villaOrderService.createVillaOrder(villaOrder);
        return Status.success().add("orderNumber",orderNumber);
    }

    //删除订单接口
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

    //删除订单接口
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
}
