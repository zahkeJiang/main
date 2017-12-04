package com.bjpygh.gzh.bean;

import java.util.Date;

public class UserCoupon {
    private Long couponId;

    private Long userId;

    private String phoneNumber;

    private Integer couponStatus;

    private Integer couponPrice;

    private Integer couponType;

    private Date couponTime;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public Integer getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }

    public Integer getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Integer couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Date getCouponTime() {
        return couponTime;
    }

    public void setCouponTime(Date couponTime) {
        this.couponTime = couponTime;
    }
}