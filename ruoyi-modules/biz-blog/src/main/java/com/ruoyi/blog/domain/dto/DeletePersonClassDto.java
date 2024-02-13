package com.ruoyi.blog.domain.dto;

import javax.validation.constraints.NotNull;

/**
 * 删除个人分类入参
 */
public class DeletePersonClassDto {

    @NotNull(message = "删除类型不能为空！")
    private Integer deleteType;

    @NotNull(message = "分类Id不能为空！")
    private Long classId;

    public Integer getDeleteType() {
        return deleteType;
    }

    public void setDeleteType(Integer deleteType) {
        this.deleteType = deleteType;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "DeletePersonClassDto{" +
                "deleteType=" + deleteType +
                ", classId=" + classId +
                '}';
    }
}
