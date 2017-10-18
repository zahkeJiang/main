package com.bjpygh.gzh.bean;

public class UserOrder {

    private String orderNumber;

    private String orderName;

    private String orderTime;

    private String orderImage;

    private String orderDescripe;

    private int orderStatus;

    private int orderPrice;

    private String realName;

    private Integer originalPrice;

    private String description;

    private String phoneNumber;

    private String dsNote; //法培方式

    public String getDsNote() {
        return dsNote;
    }

    public void setDsNote(String dsNote) {
        this.dsNote = dsNote;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(String orderImage) {
        this.orderImage = orderImage;
    }

    public String getOrderDescripe() {
        return orderDescripe;
    }

    public void setOrderDescripe(String orderDescripe) {
        this.orderDescripe = orderDescripe;
    }
}
