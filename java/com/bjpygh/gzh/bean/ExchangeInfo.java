package com.bjpygh.gzh.bean;

public class ExchangeInfo {
    private String userName;//用户姓名

    private String detail;//地址详细信息

    private String mobile;//联系方式

    private String awardName;//奖品名称

    private Double generalCoin;//兑换所需普通币

    private Double coin;//兑换所需推广币

    private String image;//奖品图片地址

    private String awardIntro;//奖品介绍

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
}
