package com.ruoyi.blog.domain.dto;

/**
 * 新建/编辑课程 接口入参
 */
public class PostCourseDto {

    private Long courseId;

    private String title;

    private String coverPic;

    private String desc;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "PostCourseDto{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", coverPic='" + coverPic + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
