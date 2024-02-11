package com.ruoyi.blog.domain.dto;


/**
 * 添加文章入参
 */
public class PostArticleDto {

    private String title;

    private Integer status;

    private Integer articleClassify;

    private Integer personClassify;

    private String content;

    private String contentFormatting;

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

    public Integer getArticleClassify() {
        return articleClassify;
    }

    public void setArticleClassify(Integer articleClassify) {
        this.articleClassify = articleClassify;
    }

    public Integer getPersonClassify() {
        return personClassify;
    }

    public void setPersonClassify(Integer personClassify) {
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

    @Override
    public String toString() {
        return "PostArticleDto{" +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", articleClassify=" + articleClassify +
                ", personClassify=" + personClassify +
                ", content='" + content + '\'' +
                ", contentFormatting='" + contentFormatting + '\'' +
                '}';
    }
}
