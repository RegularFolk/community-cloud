package com.ruoyi.blog.domain.vo;

import java.util.List;

public class CourseDetailVo {

    private CourseListVo course;

    private List<ChapterVo> chapterList;

    public static class ChapterVo {

        private Long chapterId;

        private Integer chapterOrder;

        private String title;

        private String desc;

        private List<VideoVo> videoList;

        public Long getChapterId() {
            return chapterId;
        }

        public void setChapterId(Long chapterId) {
            this.chapterId = chapterId;
        }

        public Integer getChapterOrder() {
            return chapterOrder;
        }

        public void setChapterOrder(Integer chapterOrder) {
            this.chapterOrder = chapterOrder;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<VideoVo> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<VideoVo> videoList) {
            this.videoList = videoList;
        }

        @Override
        public String toString() {
            return "ChapterVo{" +
                    "chapterId=" + chapterId +
                    ", chapterOrder=" + chapterOrder +
                    ", title='" + title + '\'' +
                    ", desc='" + desc + '\'' +
                    ", videoList=" + videoList +
                    '}';
        }
    }

    public static class VideoVo {
        private Long id;

        private String title;

        private String preview;

        private String createTime;

        private Long likeCnt;

        private Long commentCnt;

        private Long viewCnt;

        private Long collectCnt;

        private String videoId;

        private String chapterOrder;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public Long getCollectCnt() {
            return collectCnt;
        }

        public void setCollectCnt(Long collectCnt) {
            this.collectCnt = collectCnt;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getChapterOrder() {
            return chapterOrder;
        }

        public void setChapterOrder(String chapterOrder) {
            this.chapterOrder = chapterOrder;
        }

        @Override
        public String toString() {
            return "VideoVo{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", preview='" + preview + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", likeCnt=" + likeCnt +
                    ", commentCnt=" + commentCnt +
                    ", viewCnt=" + viewCnt +
                    ", collectCnt=" + collectCnt +
                    ", videoId='" + videoId + '\'' +
                    ", chapterOrder='" + chapterOrder + '\'' +
                    '}';
        }
    }

    public CourseListVo getCourse() {
        return course;
    }

    public void setCourse(CourseListVo course) {
        this.course = course;
    }

    public List<ChapterVo> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<ChapterVo> chapterList) {
        this.chapterList = chapterList;
    }

    @Override
    public String toString() {
        return "CourseDetailVo{" +
                "course=" + course +
                ", chapterList=" + chapterList +
                '}';
    }
}
