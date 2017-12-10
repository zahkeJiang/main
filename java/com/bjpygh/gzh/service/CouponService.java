package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.UserCouponExample;
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
import java.util.*;

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

    public Status getCoupons(String userid) throws ParseException {
        Date date = new Date(2592000000L);

        try{
            UserCouponExample example = new UserCouponExample();
            UserCouponExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(Long.valueOf(userid));
            List<UserCoupon> userCoupons = userCouponMapper.selectByExample(example);
            List<Map<String, String>> coupons = new ArrayList<Map<String, String>>();
            for (UserCoupon userCoupon : userCoupons){
                if(userCoupon!=null&&(userCoupon.getCouponStatus()==1||userCoupon.getCouponStatus() == 2)){
                    Date d = new Date(userCoupon.getCouponTime().getTime()+2592000000L);
                    if((new Date()).getTime()-userCoupon.getCouponTime().getTime()<date.getTime()){
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("price",userCoupon.getCouponPrice()+"");
                        map.put("type",userCoupon.getCouponType()+"");
                        if (userCoupon.getCouponType() == 2){//驾校
                            map.put("background","http://120.24.184.86/glxt/dsimage/ds_coupon_use.png");
                        }else if (userCoupon.getCouponType() == 3){//军旅
                            map.put("background","http://120.24.184.86/glxt/dsimage/ds_coupon_use.png");
                        }else if (userCoupon.getCouponType() == 1){//别墅
                            map.put("background","http://120.24.184.86/glxt/dsimage/ds_coupon_use.png");
                        }
                        map.put("date",formatter1.format(userCoupon.getCouponTime())+"-"+formatter1.format(d));
                        coupons.add(map);
                    }
                }
            }
            return Status.success().add("coupons",coupons);

        }catch (NullPointerException ne){
            return Status.fail(-40,"无优惠券");
        }

    }

    public Status getCouponsUsed(String userid) throws ParseException {
        Date date = new Date(2592000000L);
        try{
            UserCouponExample example = new UserCouponExample();
            UserCouponExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(Long.valueOf(userid));
            List<UserCoupon> userCoupons = userCouponMapper.selectByExample(example);
            List<Map<String, String>> coupons = new ArrayList<Map<String, String>>();
            for (UserCoupon userCoupon : userCoupons){
                if(userCoupon!=null&&userCoupon.getCouponStatus()==3){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("price",userCoupon.getCouponPrice()+"");
                    map.put("type",userCoupon.getCouponType()+"");
                    if (userCoupon.getCouponType() == 2){//驾校
                        map.put("background","http://120.24.184.86/glxt/dsimage/ds_coupon_used.png");
                    }else if (userCoupon.getCouponType() == 3){//军旅
                        map.put("background","http://120.24.184.86/glxt/dsimage/ds_coupon_used.png");
                    }else if (userCoupon.getCouponType() == 1){//别墅
                        map.put("background","http://120.24.184.86/glxt/dsimage/ds_coupon_used.png");
                    }
                    coupons.add(map);
                }
            }
            return Status.success().add("coupons",coupons);

        }catch (NullPointerException ne){
            return Status.fail(-40,"无优惠券");
        }

    }

    public Status getCouponsPassed(String userid) throws ParseException {
        Date date = new Date(2592000000L);

        try{
            UserCouponExample example = new UserCouponExample();
            UserCouponExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(Long.valueOf(userid));
            List<UserCoupon> userCoupons = userCouponMapper.selectByExample(example);
            List<Map<String, String>> coupons = new ArrayList<Map<String, String>>();
            for (UserCoupon userCoupon : userCoupons){
                Date d = new Date(userCoupon.getCouponTime().getTime()+2592000000L);
                if((new Date()).getTime()-userCoupon.getCouponTime().getTime()>date.getTime()){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("price",userCoupon.getCouponPrice()+"");
                    map.put("type",userCoupon.getCouponType()+"");
                    if (userCoupon.getCouponType() == 2){//驾校
                        map.put("background","http://120.24.184.86/glxt/dsimage/ds_coupon_used.png");
                    }else if (userCoupon.getCouponType() == 3){//军旅
                        map.put("background","http://120.24.184.86/glxt/dsimage/ds_coupon_used.png");
                    }else if (userCoupon.getCouponType() == 1){//别墅
                        map.put("background","http://120.24.184.86/glxt/dsimage/ds_coupon_used.png");
                    }
                    map.put("date",formatter1.format(userCoupon.getCouponTime())+"-"+formatter1.format(d));
                    coupons.add(map);
                }
            }
            return Status.success().add("coupons",coupons);

        }catch (NullPointerException ne){
            return Status.fail(-40,"无优惠券");
        }

    }

    public UserCoupon getDsCoupon(String userid) {
        UserCouponExample example = new UserCouponExample();
        UserCouponExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(userid));
        criteria.andCouponTypeEqualTo(2);
        return userCouponMapper.selectByExample(example).get(0);
    }

    public void updataCouponStatus(Map<String, String> statusMap) {
        userCouponMapper.updataCouponStatus(statusMap);
    }

    public Status getCoupon(String userid) {
        Date date = new Date(2592000000L);

        try{
            UserCouponExample example = new UserCouponExample();
            UserCouponExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(Long.valueOf(userid));
            criteria.andCouponTypeEqualTo(2);
            List<UserCoupon> userCoupons = userCouponMapper.selectByExample(example);
            if(userCoupons.size() > 0){
                UserCoupon userCoupon = userCoupons.get(0);
                if (userCoupon.getCouponStatus() == 1&&(new Date()).getTime()-userCoupon.getCouponTime().getTime()<date.getTime()){
                    return Status.success().add("price",userCoupon.getCouponPrice());
                }else {
                    return Status.fail(-20,"优惠券过期");
                }

            }else {
                return Status.fail(-40,"没有优惠券");
            }

        }catch (NullPointerException ne){
            return Status.fail(-40,"无优惠券");
        }
    }
}
