package com.ruoyi.blog.domain.vo;

import com.ruoyi.common.core.domain.UserBasicInfoVo;

/**
 * 课程列表查询出参
 */
public class CourseListVo {

    private Long courseId;

    private String title;

    private String coverPic;

    private String createTime;

    private Long viewCnt;

    private Long collectCnt;

    private Long likeCnt;

    private Long commentCnt;

    private String desc;

    private UserBasicInfoVo userBasicInfo;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public UserBasicInfoVo getUserBasicInfo() {
        return userBasicInfo;
    }

    public void setUserBasicInfo(UserBasicInfoVo userBasicInfo) {
        this.userBasicInfo = userBasicInfo;
    }

    @Override
    public String toString() {
        return "CourseListVo{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", coverPic='" + coverPic + '\'' +
                ", createTime='" + createTime + '\'' +
                ", viewCnt=" + viewCnt +
                ", collectCnt=" + collectCnt +
                ", likeCnt=" + likeCnt +
                ", commentCnt=" + commentCnt +
                ", desc='" + desc + '\'' +
                ", userBasicInfo=" + userBasicInfo +
                '}';
    }
}
