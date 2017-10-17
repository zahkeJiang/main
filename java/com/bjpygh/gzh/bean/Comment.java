package com.bjpygh.gzh.bean;

import java.util.Date;

public class Comment {
    private Integer commentId;

    private Long userId;

    private String nickname;

    private String headimageurl;

    private String content;

    private String picture;

    private Integer enterStar;

    private Integer type;

    private String commentTime;

    private Integer stayStar;

    private Integer supportStar;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getHeadimageurl() {
        return headimageurl;
    }

    public void setHeadimageurl(String headimageurl) {
        this.headimageurl = headimageurl == null ? null : headimageurl.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public Integer getEnterStar() {
        return enterStar;
    }

    public void setEnterStar(Integer enterStar) {
        this.enterStar = enterStar;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getStayStar() {
        return stayStar;
    }

    public void setStayStar(Integer stayStar) {
        this.stayStar = stayStar;
    }

    public Integer getSupportStar() {
        return supportStar;
    }

    public void setSupportStar(Integer supportStar) {
        this.supportStar = supportStar;
    }
}