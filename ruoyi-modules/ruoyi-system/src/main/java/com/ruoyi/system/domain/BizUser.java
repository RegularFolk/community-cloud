package com.ruoyi.system.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 用户业务信息表 biz_user
 */
public class BizUser extends BaseEntity {

    private Long userId;

    private Long followerCnt;

    private Long subCnt;

    private Long likeCnt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        return "BizUser{" +
                "userId=" + userId +
                ", followerCnt=" + followerCnt +
                ", subCnt=" + subCnt +
                ", likeCnt=" + likeCnt +
                "} " + super.toString();
    }
}
