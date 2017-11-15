package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.bean.UserCoupon;
import com.bjpygh.gzh.service.CouponService;
import com.bjpygh.gzh.utils.Lottery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@Controller
public class CouponController extends BaseController {

    @Autowired
    CouponService couponService;

    //优惠券抽奖接口
    @ResponseBody
    @RequestMapping(value = "/coupon.action", method = RequestMethod.POST)
    public Status Coupon(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        String isActive = userMap.get("active");
        if (couponService.getUserCoupon(userid)){
            return Status.fail(-20,"优惠券已存在");
        }else{
            if (true/*isActive!=null&&isActive.equals("true")*/){
                Lottery lottery = new Lottery();
                Map<String, Integer> map = lottery.getPrice();
                int price = map.get("price");
                int num = map.get("num");
                UserCoupon userCoupon = new UserCoupon();
                userCoupon.setUserId(Long.parseLong(userid));
                userCoupon.setCouponPrice(price);
                userCoupon.setCouponStatus(1);
                userCoupon.setCouponType(2);
                userCoupon.setCouponTime(new Date());
                couponService.insertUserCoupon(userCoupon);
                return Status.success().add("price",num);
            }else{
                return Status.fail(-30,"请先激活");
            }
        }
    }

    //跳转页面
    @RequestMapping(value = "/coupon.action", method = RequestMethod.GET)
    public String toCoupon(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return "error";
        }
        return "lottery";
    }

    //查询优惠券接口
    @ResponseBody
    @RequestMapping(value = "/queryCoupon.action", method = RequestMethod.POST)
    public Status QueryCoupon(HttpServletRequest request) throws ParseException {
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }

        return couponService.getCouponStatus(userMap.get("id"));
    }

    //跳转页面
    @RequestMapping(value = "/queryCoupon.action", method = RequestMethod.GET)
    public String toQueryCoupon(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return "error";
        }
        return "coupon";
    }

}
