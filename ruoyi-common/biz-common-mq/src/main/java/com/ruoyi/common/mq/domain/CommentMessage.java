package com.ruoyi.common.mq.domain;

import com.ruoyi.common.core.exception.ServiceException;

public class CommentMessage extends BaseMessage {

    private Long commentId;

    /**
     * {@link com.ruoyi.common.mq.enums.OperateType}
     */
    private Integer operateType;

    /**
     * {@link MessageType}
     */
    private Integer type;

    public enum MessageType {
        LIKE(1, "点赞");


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

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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
}
