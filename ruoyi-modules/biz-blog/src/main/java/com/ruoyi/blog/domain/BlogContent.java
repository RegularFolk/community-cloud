package com.ruoyi.blog.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 博客内容表 blog_content
 */
public class BlogContent extends BaseEntity {

    private Long blogId;

    private String content;

    private String contentFormatting;

    private String contentHtml;

    private Integer deleted;


    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentFormatting() {
        return contentFormatting;
    }

    public void setContentFormatting(String contentFormatting) {
        this.contentFormatting = contentFormatting;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }


    @Override
    public String toString() {
        return "BlogContent{" +
                "blogId=" + blogId +
                ", content='" + content + '\'' +
                ", contentFormatting='" + contentFormatting + '\'' +
                ", contentHtml='" + contentHtml + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
