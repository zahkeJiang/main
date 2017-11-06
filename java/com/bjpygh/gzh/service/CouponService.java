package com.bjpygh.gzh.service;

import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.bean.UserCoupon;
import com.bjpygh.gzh.dao.UserCouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class CouponService {

    @Autowired
    UserCouponMapper userCouponMapper;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy.MM.dd");

    public boolean getUserCoupon(String userid) {
        UserCoupon userCoupon = userCouponMapper.selectByPrimaryKey(Long.parseLong(userid));
        if (userCoupon!=null){
            return true;
        }else{
            return false;
        }
    }

    public void insertUserCoupon(UserCoupon userCoupon) {
        userCouponMapper.insertSelective(userCoupon);
    }

    public Status getCouponStatus(String userid) throws ParseException {
        Date date = new Date(2592000000L);
        try{
            UserCoupon userCoupon = userCouponMapper.selectByPrimaryKey(Long.parseLong(userid));
            if(userCoupon!=null&&userCoupon.getCouponStatus()==1){
                Date d = new Date(userCoupon.getCouponTime().getTime()+2592000000L);
                if((new Date()).getTime()-userCoupon.getCouponTime().getTime()<date.getTime()){
                    return Status.success().add("price",userCoupon.getCouponPrice())
                            .add("date",formatter1.format(userCoupon.getCouponTime())+"-"+formatter1.format(d));
                }else{
                    return Status.fail(-20,"优惠券已过期")
                            .add("price",userCoupon.getCouponPrice())
                            .add("date",formatter1.format(userCoupon.getCouponTime())+"-"+formatter1.format(d));
                }
            }else if(userCoupon.getCouponStatus()==2){
                Date d = new Date(userCoupon.getCouponTime().getTime()+2592000000L);
                return Status.success().add("price",userCoupon.getCouponPrice())
                        .add("date",formatter1.format(userCoupon.getCouponTime())+"-"+formatter1.format(d));
            }else if(userCoupon.getCouponStatus()==3){
                Date d = new Date(userCoupon.getCouponTime().getTime()+2592000000L);
                return Status.fail(-30,"优惠券已使用")
                        .add("price",userCoupon.getCouponPrice())
                        .add("date",formatter1.format(userCoupon.getCouponTime())+"-"+formatter1.format(d));
            }else{
                return Status.fail(-40,"无优惠券");
            }
        }catch (NullPointerException ne){
            return Status.fail(-40,"无优惠券");
        }

    }

    public UserCoupon getCoupon(String userid) {
        return userCouponMapper.selectByPrimaryKey(Long.parseLong(userid));
    }

    public void updataCouponStatus(Map<String, String> statusMap) {
        userCouponMapper.updataCouponStatus(statusMap);
    }

}
