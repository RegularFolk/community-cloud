package com.ruoyi.common.mq.domain.user;

import com.ruoyi.common.mq.domain.BaseMessage;
import com.ruoyi.common.mq.enums.OperateType;

public class UserFollowMessage extends BaseMessage {

    private Long userId;

    /**
     * {@link OperateType}
     */
    private Integer operateType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    @Override
    public String toString() {
        return "UserFollowMessage{" +
                "userId=" + userId +
                ", operateType=" + operateType +
                "} " + super.toString();
    }
}
