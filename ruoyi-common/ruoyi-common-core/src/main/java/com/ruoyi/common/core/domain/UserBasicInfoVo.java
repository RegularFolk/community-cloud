package com.ruoyi.common.core.domain;

public class UserBasicInfoVo {

    private Long id;

    private String avatar;

    private String nickName;

    private String phonenumber;

    private String sex;

    private String email;

    private Long followerCnt;

    private Long subCnt;

    private Long likeCnt;

    private String createTime;

    private Boolean followed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Boolean getFollowed() {
        return followed;
    }

    public void setFollowed(Boolean followed) {
        this.followed = followed;
    }

    @Override
    public String toString() {
        return "UserBasicInfoVo{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", followerCnt=" + followerCnt +
                ", subCnt=" + subCnt +
                ", likeCnt=" + likeCnt +
                ", createTime='" + createTime + '\'' +
                ", followed=" + followed +
                '}';
    }
}
