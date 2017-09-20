package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.DsInformation;
import com.bjpygh.gzh.bean.DsOrder;
import com.bjpygh.gzh.bean.UserCoupon;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.CouponService;
import com.bjpygh.gzh.service.DsInfoService;
import com.bjpygh.gzh.service.DsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class DsOrderController extends BaseController {

    @Autowired
    DsOrderService dsOrderService;

    @Autowired
    DsInfoService dsInfoService;

    @Autowired
    CouponService couponService;

    //改变订单状态接口
    @ResponseBody
    @RequestMapping(value = "/changeStatus.action", method = RequestMethod.POST)
    public Status ChangeOrder(HttpServletRequest request,String ordernumber){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        dsOrderService.ChageOrderStatus(ordernumber);
        return Status.success();
    }

    //查询订单接口及所属驾校
    @ResponseBody
    @RequestMapping(value = "/queryOrder.action", method = RequestMethod.POST)
    public Status QueryOrder(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        List<DsOrder> dsOrder = dsOrderService.getOrdersById(userid);
        //判断数据是否为空
        if (dsOrder == null){
            return Status.fail(-20,"数据为空");
        }else{
            for (DsOrder dso:dsOrder){
                if(dso.getOrderStatus()==1){
                    DsInformation DsInfo = dsInfoService.getDsInfoByName(dso.getDsName());
                    return Status.success().add("imageurl",DsInfo.getDsImage())
                            .add("price",dso.getOrderPrice())
                            .add("out_trade_no",dso.getOrderNumber());
                }
            }
            return Status.fail(-30,"没有已支付订单");
        }
    }


    //查询订单接口
    @ResponseBody
    @RequestMapping(value = "/selectOrder.action", method = RequestMethod.POST)
    public Status selectOrder(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        List<DsOrder> list = dsOrderService.getOrdersById(userid);
        //判断数据是否为空
        if (list == null){
            return Status.fail(-20,"没有数据");
        }else{
            return Status.success().add("dsOrders",list);
        }
    }


    //查询订单进度接口
    @ResponseBody
    @RequestMapping(value = "/schedule.action", method = RequestMethod.POST)
    public Status schedule(HttpServletRequest request,String ordernumber){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");

        DsOrder dsOrder = dsOrderService.getDsOrderByNumber(ordernumber).get(0);
        UserCoupon userCoupon = couponService.getCoupon(userid);
        return Status.success().add("dsOrder",dsOrder)
                .add("price",userCoupon.getCouponPrice());
    }

    //进度页面
    @RequestMapping(value = "/selectOrder.action", method = RequestMethod.GET)
    public String toDsInfoList(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return "error";
        }
        return "myorder";
    }

}
