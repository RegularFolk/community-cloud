package com.ruoyi.blog.domain.dto;

/**
 * 随笔列表查询入参
 */
public class BlogListDto {

    private Integer pageSize;

    private Integer pageNum;

    /**
     * {@link com.ruoyi.blog.enums.BlogQueryModeEnum}
     */
    private Integer queryMode;

    private Long userId;

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

    public Integer getQueryMode() {
        return queryMode;
    }

    public void setQueryMode(Integer queryMode) {
        this.queryMode = queryMode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BlogListDto{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", queryMode=" + queryMode +
                ", userId=" + userId +
                '}';
    }
}
