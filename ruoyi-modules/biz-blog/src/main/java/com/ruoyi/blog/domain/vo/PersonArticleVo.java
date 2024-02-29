package com.ruoyi.blog.domain.vo;

import java.util.ArrayList;
import java.util.List;

public class PersonArticleVo {

    private Long total = 0L;

    private List<Unit> list = new ArrayList<>();

    public static class Unit{
        private Long articleId;

        private String title;

        private String publishTime;

        private Long commentCnt;

        private Long viewCnt;

        private Long likeCnt;

        private Long collectCnt;

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

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public Long getCommentCnt() {
            return commentCnt;
        }

        public void setCommentCnt(Long commentCnt) {
            this.commentCnt = commentCnt;
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

        public Long getCollectCnt() {
            return collectCnt;
        }

        public void setCollectCnt(Long collectCnt) {
            this.collectCnt = collectCnt;
        }

        @Override
        public String toString() {
            return "Unit{" +
                    "articleId=" + articleId +
                    ", title='" + title + '\'' +
                    ", publishTime='" + publishTime + '\'' +
                    ", commentCnt=" + commentCnt +
                    ", viewCnt=" + viewCnt +
                    ", likeCnt=" + likeCnt +
                    ", collectCnt=" + collectCnt +
                    '}';
        }
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Unit> getList() {
        return list;
    }

    public void setList(List<Unit> list) {
        this.list = list;
    }
}
