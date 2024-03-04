package com.ruoyi.system.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 用户关注信息表 biz_follow_list
 */
public class BizFollowList extends BaseEntity {

    private Long userId;

    private Long followerId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    @Override
    public String toString() {
        return "BizFollowList{" +
                "userId=" + userId +
                ", followerId=" + followerId +
                "} " + super.toString();
    }
}
