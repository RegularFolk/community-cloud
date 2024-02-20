package com.ruoyi.blog.domain.dto;

import com.ruoyi.common.mq.enums.OperateType;

/**
 * 点赞/取消点赞 入参
 */
public class BlogLikeDto {

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
        return "BlogLikeDto{" +
                "blogId=" + blogId +
                ", operateType=" + operateType +
                '}';
    }
}
