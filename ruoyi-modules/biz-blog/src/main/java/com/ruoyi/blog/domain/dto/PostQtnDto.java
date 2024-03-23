package com.ruoyi.blog.domain.dto;

import com.ruoyi.blog.enums.BlogStatusEnum;

/**
 * 新增/修改问题入参
 */
public class PostQtnDto {

    private Long articleId;

    private String title;

    /**
     * {@link BlogStatusEnum}
     */
    private Integer status;

    private Long qtnPId;

    private String content;

    private String contentFormatting;

    private String contentHtml;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

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

    public Long getQtnPId() {
        return qtnPId;
    }

    public void setQtnPId(Long qtnPId) {
        this.qtnPId = qtnPId;
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

    @Override
    public String toString() {
        return "PostQtnDto{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", qtnPId=" + qtnPId +
                ", content='" + content + '\'' +
                ", contentFormatting='" + contentFormatting + '\'' +
                ", contentHtml='" + contentHtml + '\'' +
                '}';
    }
}
