package com.ruoyi.blog.domain.dto;

/**
 * 回答列表查询
 */
public class QtnAnsListDto {

    private Long qtnId;

    private int pageNum;

    private int pageSize;

    public Long getQtnId() {
        return qtnId;
    }

    public void setQtnId(Long qtnId) {
        this.qtnId = qtnId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "QtnAnsListDto{" +
                "qtnId=" + qtnId +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
