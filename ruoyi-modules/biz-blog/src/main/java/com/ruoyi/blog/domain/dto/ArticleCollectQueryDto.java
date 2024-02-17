package com.ruoyi.blog.domain.dto;

/**
 * 收藏管理列表查询入参
 */
public class ArticleCollectQueryDto {

    private String title;

    private Integer articleClassification;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getArticleClassification() {
        return articleClassification;
    }

    public void setArticleClassification(Integer articleClassification) {
        this.articleClassification = articleClassification;
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
        return "ArticleCollectQueryDto{" +
                "title='" + title + '\'' +
                ", articleClassification=" + articleClassification +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
