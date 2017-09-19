package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.UserCoupon;
import com.bjpygh.gzh.bean.UserCouponExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserCouponMapper {
    long countByExample(UserCouponExample example);

    int deleteByExample(UserCouponExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(UserCoupon record);

    int insertSelective(UserCoupon record);

    List<UserCoupon> selectByExample(UserCouponExample example);

    UserCoupon selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") UserCoupon record, @Param("example") UserCouponExample example);

    int updateByExample(@Param("record") UserCoupon record, @Param("example") UserCouponExample example);

    int updateByPrimaryKeySelective(UserCoupon record);

    int updateByPrimaryKey(UserCoupon record);

    void updataCouponStatus(Map<String, String> statusMap);
}