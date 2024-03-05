package com.ruoyi.common.mq.domain;

import com.ruoyi.common.core.exception.ServiceException;

public class UserMessage extends BaseMessage {

    private Long userId;

    /**
     * {@link com.ruoyi.common.mq.enums.OperateType}
     */
    private Integer operateType;

    /**
     * {@link MessageType}
     */
    private Integer type;

    public enum MessageType {
        FOLLOW(1, "关注数增加");

        private final Integer type;

        private final String typeDesc;

        MessageType(Integer type, String typeDesc) {
            this.type = type;
            this.typeDesc = typeDesc;
        }

        public Integer getType() {
            return type;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public static MessageType getEnum(Integer type) {
            for (MessageType value : values()) {
                if (value.getType().equals(type)) {
                    return value;
                }
            }
            throw new ServiceException("枚举不存在！");
        }
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "userId=" + userId +
                ", operateType=" + operateType +
                ", type=" + type +
                "} " + super.toString();
    }
}
