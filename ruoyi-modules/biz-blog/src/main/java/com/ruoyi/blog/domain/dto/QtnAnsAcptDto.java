package com.ruoyi.blog.domain.dto;

public class QtnAnsAcptDto {

    private Long qtnId;

    private Long ansId;

    public Long getQtnId() {
        return qtnId;
    }

    public void setQtnId(Long qtnId) {
        this.qtnId = qtnId;
    }

    public Long getAnsId() {
        return ansId;
    }

    public void setAnsId(Long ansId) {
        this.ansId = ansId;
    }

    @Override
    public String toString() {
        return "QtnAnsAcptDto{" +
                "qtnId=" + qtnId +
                ", ansId=" + ansId +
                '}';
    }
}
