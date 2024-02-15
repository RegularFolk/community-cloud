package com.ruoyi.blog.domain.dto;

import com.ruoyi.blog.enums.CommentOrderEnum;

public class GetCommentDto {

    private Long blogId;

    private Long start;

    // 默认不刷新
    private Boolean refreshFlag = false;

    // 默认排序方式为时间升序
    private String order = CommentOrderEnum.TIME_ASC.getOrder();

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Boolean getRefreshFlag() {
        return refreshFlag;
    }

    public void setRefreshFlag(Boolean refreshFlag) {
        this.refreshFlag = refreshFlag;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "GetCommentDto{" +
                "blogId=" + blogId +
                ", start=" + start +
                ", refreshFlag=" + refreshFlag +
                ", order='" + order + '\'' +
                '}';
    }
}
