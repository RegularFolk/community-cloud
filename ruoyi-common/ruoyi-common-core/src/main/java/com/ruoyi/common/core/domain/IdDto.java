package com.ruoyi.common.core.domain;

import javax.validation.constraints.NotNull;

/**
 * 单个Id的入参，通用Dto
 */
public class IdDto {

    @NotNull(message = "Id不可为空！")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdDto{" +
                "id=" + id +
                '}';
    }
}
