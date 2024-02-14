package com.ruoyi.blog.domain.dto;


/**
 * 添加文章入参
 */
public class PostArticleDto {

    private Long articleId;

    private String title;

    private Integer status;

    private Long articleClassify;

    private Long personClassify;

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

    public Long getArticleClassify() {
        return articleClassify;
    }

    public void setArticleClassify(Long articleClassify) {
        this.articleClassify = articleClassify;
    }

    public Long getPersonClassify() {
        return personClassify;
    }

    public void setPersonClassify(Long personClassify) {
        this.personClassify = personClassify;
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
        return "PostArticleDto{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", articleClassify=" + articleClassify +
                ", personClassify=" + personClassify +
                ", content='" + content + '\'' +
                ", contentFormatting='" + contentFormatting + '\'' +
                ", contentHtml='" + contentHtml + '\'' +
                '}';
    }
}
