package com.ruoyi.blog.domain.dto;

/**
 * 发表想法入参
 */
public class PostBlogDto {

    private String content;

    private String picUrls;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    @Override
    public String toString() {
        return "PostBlogDto{" +
                "content='" + content + '\'' +
                ", picUrls='" + picUrls + '\'' +
                '}';
    }
}
