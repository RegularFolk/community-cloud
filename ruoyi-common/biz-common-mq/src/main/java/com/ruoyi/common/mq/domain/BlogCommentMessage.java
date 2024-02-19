package com.ruoyi.common.mq.domain;

import com.ruoyi.common.mq.enums.OperateType;

public class BlogCommentMessage {

    private Long messageId;

    private Long blogId;

    /**
     * {@link OperateType}
     */
    private Integer operateType;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    @Override
    public String toString() {
        return "BlogCommentMessage{" +
                "messageId=" + messageId +
                ", blogId=" + blogId +
                ", operateType=" + operateType +
                '}';
    }
}
