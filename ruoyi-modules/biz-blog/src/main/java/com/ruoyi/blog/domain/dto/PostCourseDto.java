package com.ruoyi.blog.domain.dto;

import java.util.List;

/**
 * 新建/编辑课程 接口入参
 */
public class PostCourseDto {

    private Long courseId;

    private CourseDto course;

    private ChapterDto chapterList;

    public static class CourseDto{
        private String  coverPic;
        private String title;
        private String desc;

        public String getCoverPic() {
            return coverPic;
        }

        public void setCoverPic(String coverPic) {
            this.coverPic = coverPic;
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

        @Override
        public String toString() {
            return "CourseDto{" +
                    "coverPic='" + coverPic + '\'' +
                    ", title='" + title + '\'' +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }

    public static class ChapterDto{
        private String desc;
        private String title;
        private List<VideoDto> vodList;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<VideoDto> getVodList() {
            return vodList;
        }

        public void setVodList(List<VideoDto> vodList) {
            this.vodList = vodList;
        }

        @Override
        public String toString() {
            return "ChapterDto{" +
                    "desc='" + desc + '\'' +
                    ", title='" + title + '\'' +
                    ", vodList=" + vodList +
                    '}';
        }
    }

    private static class VideoDto{
        private String title;
        private String preview;
        private String videoId;

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

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        @Override
        public String toString() {
            return "VideoDto{" +
                    "title='" + title + '\'' +
                    ", preview='" + preview + '\'' +
                    ", videoId='" + videoId + '\'' +
                    '}';
        }
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

    public ChapterDto getChapterList() {
        return chapterList;
    }

    public void setChapterList(ChapterDto chapterList) {
        this.chapterList = chapterList;
    }

    @Override
    public String toString() {
        return "PostCourseDto{" +
                "courseId=" + courseId +
                ", course=" + course +
                ", chapterList=" + chapterList +
                '}';
    }
}
