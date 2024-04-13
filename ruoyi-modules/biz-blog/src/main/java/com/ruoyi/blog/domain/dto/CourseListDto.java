package com.ruoyi.blog.domain.dto;

/**
 * 课程列表查询接口入参
 */
public class CourseListDto {

    /**
     * {@link com.ruoyi.blog.enums.CourseListTypeEnum}
     */
    private Integer listType;

    private Integer pageSize;

    private Integer pageNum;

    private Long userId;

    private String title;

    public Integer getListType() {
        return listType;
    }

    public void setListType(Integer listType) {
        this.listType = listType;
    }

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
