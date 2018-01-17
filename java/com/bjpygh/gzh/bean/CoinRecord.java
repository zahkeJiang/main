package com.bjpygh.gzh.bean;

import java.util.Date;

public class CoinRecord {
    private Long recordId;

    private Long userId;

    private int generalValue;

    private int coinValue;

    private int record_type;

    private String note;

    private String createTime;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getGeneralValue() {
        return generalValue;
    }

    public void setGeneralValue(int generalValue) {
        this.generalValue = generalValue;
    }

    public int getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(int coinValue) {
        this.coinValue = coinValue;
    }

    public int getRecord_type() {
        return record_type;
    }

    public void setRecord_type(int record_type) {
        this.record_type = record_type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CoinRecord{" +
                "recordId=" + recordId +
                ", userId=" + userId +
                ", generalValue=" + generalValue +
                ", coinValue=" + coinValue +
                ", record_type=" + record_type +
                ", note='" + note + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
