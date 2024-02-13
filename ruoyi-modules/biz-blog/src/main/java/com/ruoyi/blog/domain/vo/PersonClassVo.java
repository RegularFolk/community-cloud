package com.ruoyi.blog.domain.vo;

public class PersonClassVo {

    private Long value;

    private String label;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "PersonClassVo{" +
                "value=" + value +
                ", label='" + label + '\'' +
                '}';
    }
}
