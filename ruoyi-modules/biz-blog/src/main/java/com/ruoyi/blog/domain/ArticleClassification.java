package com.ruoyi.blog.domain;

/**
 * 个人分类表 article_classification
 */
public class ArticleClassification {

    private Long id;

    private Long userId;

    private String className;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "ArticleClassification{" +
                "id=" + id +
                ", userId=" + userId +
                ", className=" + className +
                '}';
    }
}
