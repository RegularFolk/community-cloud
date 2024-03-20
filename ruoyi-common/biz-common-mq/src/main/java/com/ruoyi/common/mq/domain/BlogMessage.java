package com.ruoyi.common.mq.domain;

import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.mq.enums.BlogTypeEnum;

public class BlogMessage extends BaseMessage {

    private Long blogId;

    /**
     * {@link com.ruoyi.common.mq.enums.OperateType}
     */
    private Integer operateType;

    /**
     * {@link BlogTypeEnum}
     */
    private Integer blogType;

    /**
     * {@link MessageType}
     */
    private Integer type;

    public enum MessageType {
        LIKE(1, "点赞"),
        VIEW(2, "浏览"),
        COMMENT(3, "评论"),
        COLLECT(4, "收藏");

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

    public Integer getBlogType() {
        return blogType;
    }

    public void setBlogType(Integer blogType) {
        this.blogType = blogType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BlogMessage{" +
                "blogId=" + blogId +
                ", operateType=" + operateType +
                ", blogType=" + blogType +
                ", type=" + type +
                "} " + super.toString();
    }
}
