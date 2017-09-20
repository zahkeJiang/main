package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.*;
import com.bjpygh.gzh.entity.DsAliPay;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.CouponService;
import com.bjpygh.gzh.service.DsInfoService;
import com.bjpygh.gzh.service.DsOrderService;
import com.bjpygh.gzh.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class DsOrderController extends BaseController{

    @Autowired
    DsOrderService dsOrderService;

    @Autowired
    DsInfoService dsInfoService;

    @Autowired
    CouponService couponService;

    @Autowired
    PackageService packageService;

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

    //创建订单接口
    @ResponseBody
    @RequestMapping(value = "/createOrder.action", method = RequestMethod.POST)
    public Status createOrder(HttpServletRequest request,DsAliPay dsAliPay){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        String packageid = dsAliPay.getPackageid();
        String select = dsAliPay.getSelect();
        int couponprice = 0;

        if (select.equals("1")){
            UserCoupon userCoupon = couponService.getCoupon(userid);
            Date date = new Date(604800000L);

            if(userCoupon.getCouponStatus()==1&&userCoupon!=null){
                if((new Date()).getTime()-userCoupon.getCouponTime().getTime()<date.getTime()){
                    couponprice = userCoupon.getCouponPrice();
                }else if(userCoupon.getCouponStatus()==2){
                    couponprice = userCoupon.getCouponPrice();
                }else{
                    couponprice = 0;
                }
            }else{
                couponprice = 0;
            }
        }else{
            couponprice = 0;
        }

        User user = userService.getUserById(userid);
        DsPackage dsPackage = packageService.getPackage(packageid);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String out_trade_no ="DSPYGH" + year + month + date + hour + minute + second + userid;

        int total_amount = dsPackage.getPrice()-couponprice;

        DsOrder dsOrder = new DsOrder();
        DsInformation DsInfo = dsInfoService.getDsInfoByName(dsPackage.getDsName());
        dsOrder.setUserId(Long.parseLong(userid));
        dsOrder.setDsName(dsPackage.getDsName());
        dsOrder.setDsType(dsPackage.getDsType());
        dsOrder.setModels(dsPackage.getModels());
        dsOrder.setDescription(dsPackage.getDescription());
        dsOrder.setAddress(user.getAddress());
        dsOrder.setNote(user.getReamark());
        dsOrder.setRealName(user.getRealName());
        dsOrder.setOrderNumber(out_trade_no);
        dsOrder.setOrderPrice(total_amount);
        dsOrder.setOriginalPrice(dsPackage.getPrice());
        dsOrder.setOrderStatus((byte) 0);
        dsOrder.setPhoneNumber(user.getPhoneNumber());
        dsOrder.setTrainTime(dsPackage.getTrainTime());
        dsOrder.setImageurl(DsInfo.getDsImage());
        dsOrderService.insertOrder(dsOrder);

        return Status.success().add("ordernumber",out_trade_no);
    }

    //删除订单接口
    @ResponseBody
    @RequestMapping(value = "/deleteOrder.action", method = RequestMethod.POST)
    public Status delOrder(HttpServletRequest request,String ordernumber){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        dsOrderService.deleteOrderByNum(ordernumber);
        return Status.success();
    }
}
