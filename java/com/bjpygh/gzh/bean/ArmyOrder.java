package com.bjpygh.gzh.bean;

import java.util.Date;

public class ArmyOrder {
    private Integer armyId;

    private Long userId;

    private String realName;

    private String armyName;

    private Integer armyPrice;

    private String date;

    private String createTime;

    private String payTime;

    private String refundTime;

    private String finishTime;

    private String note;

    private Integer originalPrice;

    private Integer peopleNumber;

    private String orderNumber;

    private Integer orderStatus;

    private Integer roomNumber;

    private Integer noroomNumber;

    private String imageurl;

    private Byte payType;

    private String idNumber;

    private Integer insurance;

    private Byte fullAmount;

    private Byte barbecue;

    private Byte period;

    public Integer getArmyId() {
        return armyId;
    }

    public void setArmyId(Integer armyId) {
        this.armyId = armyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getArmyName() {
        return armyName;
    }

    public void setArmyName(String armyName) {
        this.armyName = armyName == null ? null : armyName.trim();
    }

    public Integer getArmyPrice() {
        return armyPrice;
    }

    public void setArmyPrice(Integer armyPrice) {
        this.armyPrice = armyPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getNoroomNumber() {
        return noroomNumber;
    }

    public void setNoroomNumber(Integer noroomNumber) {
        this.noroomNumber = noroomNumber;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl == null ? null : imageurl.trim();
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public Integer getInsurance() {
        return insurance;
    }

    public void setInsurance(Integer insurance) {
        this.insurance = insurance;
    }

    public Byte getFullAmount() {
        return fullAmount;
    }

    public void setFullAmount(Byte fullAmount) {
        this.fullAmount = fullAmount;
    }

    public Byte getBarbecue() {
        return barbecue;
    }

    public void setBarbecue(Byte barbecue) {
        this.barbecue = barbecue;
    }

    public Byte getPeriod() {
        return period;
    }

    public void setPeriod(Byte period) {
        this.period = period;
    }
}