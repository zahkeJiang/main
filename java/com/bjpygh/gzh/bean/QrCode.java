package com.bjpygh.gzh.bean;

public class QrCode {
    private Long userId;

    private String ticket;

    private Integer concern;

    private Integer unconcern;

    private Integer onconcern;

    private Integer concerned;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    public Integer getConcern() {
        return concern;
    }

    public void setConcern(Integer concern) {
        this.concern = concern;
    }

    public Integer getUnconcern() {
        return unconcern;
    }

    public void setUnconcern(Integer unconcern) {
        this.unconcern = unconcern;
    }

    public Integer getOnconcern() {
        return onconcern;
    }

    public void setOnconcern(Integer onconcern) {
        this.onconcern = onconcern;
    }

    public Integer getConcerned() {
        return concerned;
    }

    public void setConcerned(Integer concerned) {
        this.concerned = concerned;
    }
}