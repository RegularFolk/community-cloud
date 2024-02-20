package com.ruoyi.common.mq.domain;

import com.ruoyi.common.mq.enums.OperateType;

public class BlogLikeMessage extends BaseMessage{


    private Long blogId;

    /**
     * {@link OperateType}
     */
    private Integer operateType;

    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BlogLikeMessage{" +
                "messageId=" + getMessageId() +
                ", blogId=" + blogId +
                ", operateType=" + operateType +
                ", userId=" + userId +
                '}';
    }
}
