package com.ruoyi.blog.domain.vo;

public class BlogRankVo {

    private Long blogId;

    private String title;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BlogRankVo{" +
                "blogId=" + blogId +
                ", title='" + title + '\'' +
                '}';
    }
}
