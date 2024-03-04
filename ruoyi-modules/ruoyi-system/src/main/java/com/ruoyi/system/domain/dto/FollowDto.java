package com.ruoyi.system.domain.dto;

import com.ruoyi.common.mq.enums.OperateType;

public class FollowDto {

    private Long followId;

    /**
     * {@link OperateType}
     */
    private Integer operateType;

    public Long getFollowId() {
        return followId;
    }

    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    @Override
    public String toString() {
        return "FollowDto{" +
                "followId=" + followId +
                ", operateType=" + operateType +
                '}';
    }
}
