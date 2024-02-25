package com.ruoyi.blog.domain.vo;

import java.util.List;

/**
 * 获取想法返回值
 */
public class BlogDetailVo {

    private String content;

    private Long viewCnt;

    private Long likeCnt;

    private Long commentCnt;

    private List<String> picUrlList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(Long viewCnt) {
        this.viewCnt = viewCnt;
    }

    public Long getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Long likeCnt) {
        this.likeCnt = likeCnt;
    }

    public Long getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Long commentCnt) {
        this.commentCnt = commentCnt;
    }

    public List<String> getPicUrlList() {
        return picUrlList;
    }

    public void setPicUrlList(List<String> picUrlList) {
        this.picUrlList = picUrlList;
    }

    @Override
    public String toString() {
        return "BlogDetailVo{" +
                "content='" + content + '\'' +
                ", viewCnt=" + viewCnt +
                ", likeCnt=" + likeCnt +
                ", commentCnt=" + commentCnt +
                ", picUrlList=" + picUrlList +
                '}';
    }
}
