package com.ruoyi.blog.domain.dto;

/**
 * 随笔管理列表查询入参
 */
public class ArticleQueryDto {

    private String title;

    private Integer status;

    private Long articleClassification;

    private Long personClassification;

    private Integer pageNum;

    private Integer pageSize;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getArticleClassification() {
        return articleClassification;
    }

    public void setArticleClassification(Long articleClassification) {
        this.articleClassification = articleClassification;
    }

    public Long getPersonClassification() {
        return personClassification;
    }

    public void setPersonClassification(Long personClassification) {
        this.personClassification = personClassification;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "ArticleQueryDto{" +
                "title='" + title + '\'' +
                ", status=" + status +
                ", articleClassification=" + articleClassification +
                ", personClassification=" + personClassification +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
