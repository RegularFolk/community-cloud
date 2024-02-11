package com.ruoyi.blog.domain.dto;

/**
 * 随笔管理列表查询入参
 */
public class ArticleQueryDto {

    private String title;

    private Integer status;

    private Integer articleClassification;

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

    public Integer getArticleClassification() {
        return articleClassification;
    }

    public void setArticleClassification(Integer articleClassification) {
        this.articleClassification = articleClassification;
    }

    @Override
    public String toString() {
        return "ArticleQueryDto{" +
                "title='" + title + '\'' +
                ", status=" + status +
                ", articleClassification=" + articleClassification +
                '}';
    }
}
