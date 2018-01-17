package com.bjpygh.gzh.bean;

import java.util.Date;

public class Integral {

    private Long userId;

    private Double generalCoin;  //普通币

    private Double coin;  //推广币

    private int isSign;  //是否签到

    private int continuous;  //签到次数

    private Date createTime;

    private Date updateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getGeneralCoin() {
        return generalCoin;
    }

    public void setGeneralCoin(Double generalCoin) {
        this.generalCoin = generalCoin;
    }

    public Double getCoin() {
        return coin;
    }

    public void setCoin(Double coin) {
        this.coin = coin;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }

    public int getContinuous() {
        return continuous;
    }

    public void setContinuous(int continuous) {
        this.continuous = continuous;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Integral{" +
                "userId=" + userId +
                ", generalCoin=" + generalCoin +
                ", coin=" + coin +
                ", isSign=" + isSign +
                ", continuous=" + continuous +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
