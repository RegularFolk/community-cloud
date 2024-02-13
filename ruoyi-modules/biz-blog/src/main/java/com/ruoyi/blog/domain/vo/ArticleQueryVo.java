package com.ruoyi.blog.domain.vo;

import java.util.List;

public class ArticleQueryVo {

    // id 用于前端table渲染，无实际业务含义
    private Integer id;

    private Long classId;

    private String className;

    private String createTime;

    private final Boolean isClass = true;

    private List<ArticleQueryChild> children;

    public static class ArticleQueryChild {
        // id 用于前端table渲染，无实际业务含义
        private Integer id;

        private Long articleId;

        private String title;

        private String preview;

        private Integer status;

        private String articleClassify;

        private String publishTime;

        private String createTime;

        private Long likeCnt;

        private Long commentCnt;

        private Long viewCnt;

        private final Boolean isClass = false;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

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

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getArticleClassify() {
            return articleClassify;
        }

        public void setArticleClassify(String articleClassify) {
            this.articleClassify = articleClassify;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public Long getViewCnt() {
            return viewCnt;
        }

        public void setViewCnt(Long viewCnt) {
            this.viewCnt = viewCnt;
        }

        public Boolean getIsClass() {
            return isClass;
        }

        @Override
        public String toString() {
            return "ArticleQueryChild{" +
                    "id=" + id +
                    ", articleId=" + articleId +
                    ", title='" + title + '\'' +
                    ", preview='" + preview + '\'' +
                    ", status=" + status +
                    ", articleClassify='" + articleClassify + '\'' +
                    ", publishTime='" + publishTime + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", likeCnt=" + likeCnt +
                    ", commentCnt=" + commentCnt +
                    ", viewCnt=" + viewCnt +
                    ", isClass=" + isClass +
                    '}';
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<ArticleQueryChild> getChildren() {
        return children;
    }

    public void setChildren(List<ArticleQueryChild> children) {
        this.children = children;
    }

    public Boolean getIsClass() {
        return isClass;
    }

    @Override
    public String toString() {
        return "ArticleQueryVo{" +
                "id=" + id +
                ", classId=" + classId +
                ", className='" + className + '\'' +
                ", createTime='" + createTime + '\'' +
                ", isClass=" + isClass +
                ", children=" + children +
                '}';
    }
}
