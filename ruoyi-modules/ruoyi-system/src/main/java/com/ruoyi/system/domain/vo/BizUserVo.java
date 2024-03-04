package com.ruoyi.system.domain.vo;

public class BizUserVo {

    private Long userId;

    private String userName;

    private String avatar;

    private Boolean isFollowed;

    private Long followerCnt;

    private Long subCnt;

    private Long likeCnt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getFollowed() {
        return isFollowed;
    }

    public void setFollowed(Boolean followed) {
        isFollowed = followed;
    }

    public Long getFollowerCnt() {
        return followerCnt;
    }

    public void setFollowerCnt(Long followerCnt) {
        this.followerCnt = followerCnt;
    }

    public Long getSubCnt() {
        return subCnt;
    }

    public void setSubCnt(Long subCnt) {
        this.subCnt = subCnt;
    }

    public Long getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Long likeCnt) {
        this.likeCnt = likeCnt;
    }

    @Override
    public String toString() {
        return "BizUserVo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", isFollowed=" + isFollowed +
                ", followerCnt=" + followerCnt +
                ", subCnt=" + subCnt +
                ", likeCnt=" + likeCnt +
                '}';
    }
}
