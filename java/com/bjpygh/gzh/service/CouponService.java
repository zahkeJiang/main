package com.bjpygh.gzh.service;

import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.bean.UserCoupon;
import com.bjpygh.gzh.dao.UserCouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

@Service
public class CouponService {

    @Autowired
    UserCouponMapper userCouponMapper;

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

    public Status getCouponStatus(String userid) {
        Date date = new Date(2419200000L);
        try{
            UserCoupon userCoupon = userCouponMapper.selectByPrimaryKey(Long.parseLong(userid));
            if(userCoupon!=null&&userCoupon.getCouponStatus()==1){
                if((new Date()).getTime()-userCoupon.getCouponTime().getTime()<date.getTime()){
                    return Status.success().add("price",userCoupon.getCouponPrice());
                }else{
                    return Status.fail(-20,"优惠券已过期")
                            .add("price",userCoupon.getCouponPrice());
                }
            }else if(userCoupon.getCouponStatus()==2){
                return Status.success().add("price",userCoupon.getCouponPrice());
            }else if(userCoupon.getCouponStatus()==3){
                return Status.fail(-30,"优惠券已使用")
                        .add("price",userCoupon.getCouponPrice());
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
