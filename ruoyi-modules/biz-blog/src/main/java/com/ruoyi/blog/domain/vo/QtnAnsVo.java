package com.ruoyi.blog.domain.vo;

import java.util.List;

/**
 * 回答列表查询出参
 */
public class QtnAnsVo {

    private int total;

    private List<ArticleVo> ansList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ArticleVo> getAnsList() {
        return ansList;
    }

    public void setAnsList(List<ArticleVo> ansList) {
        this.ansList = ansList;
    }

    @Override
    public String toString() {
        return "QtnAnsVo{" +
                "total=" + total +
                ", ansList=" + ansList +
                '}';
    }
}

