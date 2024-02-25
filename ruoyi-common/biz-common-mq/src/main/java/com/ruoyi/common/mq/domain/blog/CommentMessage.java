package com.ruoyi.common.mq.domain.blog;

import com.ruoyi.common.mq.domain.BaseMessage;
import com.ruoyi.common.mq.enums.OperateType;

public class CommentMessage extends BaseMessage {

    private Long blogId;

    /**
     * {@link OperateType}
     */
    private Integer operateType;

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
        return "CommentMessage{" +
                "messageId=" + getMessageId() +
                ", blogId=" + blogId +
                ", operateType=" + operateType +
                '}';
    }
}
