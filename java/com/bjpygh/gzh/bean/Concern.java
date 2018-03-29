package com.bjpygh.gzh.bean;

import java.util.Date;

public class Concern {
    private Long concernId;

    private Long userId;

    private String openid;

    private Boolean isDelete;

    private Boolean concerned;

    private String createTime;

    public Long getConcernId() {
        return concernId;
    }

    public void setConcernId(Long concernId) {
        this.concernId = concernId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getConcerned() {
        return concerned;
    }

    public void setConcerned(Boolean concerned) {
        this.concerned = concerned;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}