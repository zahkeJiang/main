package com.bjpygh.gzh.bean;

/**
 * 兑换记录
 */
public class Exchange {
    private Long exchangeId; //兑换记录ID

    private Long addressId;//地址ID

    private Long awardId;//兑换奖品ID

    private Integer status;//兑换状态

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

    @Override
    public String toString() {
        return "Exchange{" +
                "exchangeId=" + exchangeId +
                ", addressId=" + addressId +
                ", awardId=" + awardId +
                ", status=" + status +
                '}';
    }
}
