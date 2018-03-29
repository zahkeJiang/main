package com.bjpygh.gzh.entity;

public class DsAliPay {

    private String userid;

    private String packageid;

    private String select;

    private Byte protecttion;

    public Byte getProtecttion() {
        return protecttion;
    }

    public void setProtecttion(Byte protecttion) {
        this.protecttion = protecttion;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPackageid() {
        return packageid;
    }

    public void setPackageid(String packageid) {
        this.packageid = packageid;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}
