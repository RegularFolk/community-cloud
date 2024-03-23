package com.ruoyi.blog.domain.dto;

import com.ruoyi.blog.enums.BlogOrderEnum;

public class QtnSquareDto {

    private Integer pageSize;

    private Integer pageNum;

    /**
     * {@link BlogOrderEnum}
     */
    private Integer blogOrder;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getBlogOrder() {
        return blogOrder;
    }

    public void setBlogOrder(Integer blogOrder) {
        this.blogOrder = blogOrder;
    }

    @Override
    public String toString() {
        return "QtnSquareDto{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", blogOrder=" + blogOrder +
                '}';
    }
}
