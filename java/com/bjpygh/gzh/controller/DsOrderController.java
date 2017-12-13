package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.*;
import com.bjpygh.gzh.entity.DsAliPay;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.CouponService;
import com.bjpygh.gzh.service.DsInfoService;
import com.bjpygh.gzh.service.DsOrderService;
import com.bjpygh.gzh.service.PackageService;
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
public class DsOrderController extends BaseController {

    @Autowired
    DsOrderService dsOrderService;

    @Autowired
    DsInfoService dsInfoService;

    @Autowired
    CouponService couponService;

    @Autowired
    PackageService packageService;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

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
        //判断数据是否有内容
        if (!(list.size()>0)){
            return Status.fail(-20,"没有数据");
        }else{
            return Status.success().add("dsOrders",list);
        }
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
    public Status createOrder(HttpServletRequest request,DsAliPay dsAliPay) throws ParseException {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");

        List<DsOrder> dsos = new ArrayList<DsOrder>();
        List<DsOrder> list = dsOrderService.getOrdersById(userid);
        for (DsOrder dso : list){
            if (dso.getOrderStatus() == 0){
                dsos.add(dso);
            }
        }
        if (dsos.size()>2){
            return Status.fail(-40,"您已创建三个订单，无法创建更多订单");
        }
        String packageid = dsAliPay.getPackageid();
        String select = dsAliPay.getSelect();
        int couponprice = 0;

        System.out.println("select+"+select);
        if (select.equals("1")){
            UserCoupon userCoupon = couponService.getDsCoupon(userid);
            Date date = new Date(2419200000L);

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

        if (dsPackage.getMustProtection() == 2){
            if (dsAliPay.getProtecttion() == 1){
                dsOrder.setProtecttion((byte) 1);
                total_amount += 180;
            }else {
                dsOrder.setProtecttion((byte) 0);
            }
        }else if (dsPackage.getMustProtection() == 1){
            dsOrder.setProtecttion((byte) 1);
        }else if (dsPackage.getMustProtection() == 0){
            dsOrder.setProtecttion((byte) 0);
        }

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
        String imageUrl = "http://120.24.184.86/glxt/dsimage/"+DsInfo.getDsImage().split(",")[0];
        dsOrder.setImageurl(imageUrl);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dsOrder.setCreateTime(formatter.format(new Date()));
        dsOrderService.insertOrder(dsOrder);

        OrderPush orderPush = new OrderPush();
        Map<String, String> map = new HashMap<String, String>();
        map.put("first","Hi,您已成功提交驾考订单");
        map.put("orderID",out_trade_no);
        map.put("orderMoneySum",total_amount+"元");
        map.put("remark","请尽快支付，如有问题咨询客服：010-59822296");
        map.put("openid",userMap.get("openid"));

//        orderPush.CreateJsonObj(map);

        return Status.success().add("ordernumber",out_trade_no);
    }

    //关闭订单接口
    @ResponseBody
    @RequestMapping(value = "/cancelOrder.action", method = RequestMethod.POST)
    public Status delOrder(HttpServletRequest request,String ordernumber){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        dsOrderService.updateOrderStatus(ordernumber);
        return Status.success();
    }

    @RequestMapping(value = "/schedule.action", method = RequestMethod.GET)
    public String toOrder(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return "error";
        }
        return "myorder";
    }
}
