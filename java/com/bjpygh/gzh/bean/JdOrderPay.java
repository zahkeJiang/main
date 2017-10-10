package com.bjpygh.gzh.bean;

public class JdOrderPay {

    private String version; //当前固定填写：V2.0

    private String sign; //用户交易信息签名后的值

    private String merchant; //商户号（由京东分配）

    private String tradeNum; //商户唯一交易流水号。格式：字母&数字

    private String tradeName; //商户订单的标题/商品名称/关键字等

    private String tradeTime; //订单生成时间。格式：“yyyyMMddHHmmss”

    private String amount; //商户订单的资金总额。单位：分，大于0

    private String orderType; //固定值：0或者1 （0：实物，1：虚拟）

    private String currency; //货币类型，固定填CNY

    private String callbackUrl; //支付成功后跳转的URL

    private String notifyUrl; //支付完成后，京东异步通知商户服务相关支付结果。必须是外网可访问的url。

    private String userId; //商户平台用户的唯一账号。注：用户账号是商户端系统的用户唯一账号。

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
