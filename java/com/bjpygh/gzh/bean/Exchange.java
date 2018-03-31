package com.bjpygh.gzh.bean;

/**
 * 兑换记录
 */
public class Exchange {
    private Long exchangeId; //兑换记录ID

    private Long addressId;//地址ID

    private Long awardId;//兑换奖品ID

    private Integer status;//兑换状态

    private String number;  //兑换状态 0：兑换成功，未发货 1：已发货，物流中 2：已收货

    private String logistics; //物流

    private String crateTime;

    public Long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getAwardId() {
        return awardId;
    }

    public void setAwardId(Long awardId) {
        this.awardId = awardId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public String getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(String crateTime) {
        this.crateTime = crateTime;
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "exchangeId=" + exchangeId +
                ", addressId=" + addressId +
                ", awardId=" + awardId +
                ", status=" + status +
                ", number='" + number + '\'' +
                ", logistics='" + logistics + '\'' +
                ", crateTime='" + crateTime + '\'' +
                '}';
    }
}
