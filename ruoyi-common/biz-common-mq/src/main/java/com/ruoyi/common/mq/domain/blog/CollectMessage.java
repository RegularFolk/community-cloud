package com.ruoyi.common.mq.domain.blog;

import com.ruoyi.common.mq.domain.BaseMessage;
import com.ruoyi.common.mq.enums.OperateType;

public class CollectMessage extends BaseMessage {

    private Long blogId;

    /**
     * {@link OperateType}
     */
    private OperateType operateType;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public OperateType getOperateType() {
        return operateType;
    }

    public void setOperateType(OperateType operateType) {
        this.operateType = operateType;
    }

    @Override
    public String toString() {
        return "CollectMessage{" +
                "blogId=" + blogId +
                ", operateType=" + operateType +
                "} " + super.toString();
    }
}
