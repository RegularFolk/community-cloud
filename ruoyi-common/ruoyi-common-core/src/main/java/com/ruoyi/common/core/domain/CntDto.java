package com.ruoyi.common.core.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CntDto {

    private Long id;

    private Long cnt;

    public static Map<Long, List<CntDto>> convert2Map(List<CntDto> dtoList) {
        return dtoList.stream().collect(Collectors.groupingBy(CntDto::getId));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCnt() {
        return cnt;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "CntDto{" +
                "id=" + id +
                ", cnt=" + cnt +
                '}';
    }
}
