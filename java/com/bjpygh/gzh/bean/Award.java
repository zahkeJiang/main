package com.bjpygh.gzh.bean;

public class Award {
    private Long awardId;//奖品ID

    private String awardName;//奖品名称

    private Double generalCoin;//兑换所需普通币

    private Double coin;//兑换所需推广币

    private Integer amount;//奖品数量

    private String image;//奖品图片地址

    private String awardIntro;//奖品介绍

    private Integer status;//奖品状态

    private String expireTime;//奖品到期时间

    public Long getAwardId() {
        return awardId;
    }

    public void setAwardId(Long awardId) {
        this.awardId = awardId;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAwardIntro() {
        return awardIntro;
    }

    public void setAwardIntro(String awardIntro) {
        this.awardIntro = awardIntro;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "Award{" +
                "awardId=" + awardId +
                ", awardName='" + awardName + '\'' +
                ", generalCoin=" + generalCoin +
                ", coin=" + coin +
                ", amount=" + amount +
                ", image='" + image + '\'' +
                ", awardIntro='" + awardIntro + '\'' +
                ", status=" + status +
                ", expireTime='" + expireTime + '\'' +
                '}';
    }
}
