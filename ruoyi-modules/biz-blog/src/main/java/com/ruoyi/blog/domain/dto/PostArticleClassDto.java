package com.ruoyi.blog.domain.dto;

/**
 * 添加个人分类入参
 */
public class PostArticleClassDto {

    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "PostArticleClassDto{" +
                ", className='" + className + '\'' +
                '}';
    }
}
