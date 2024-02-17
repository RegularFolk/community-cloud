package com.ruoyi.blog.domain.vo;


import java.util.List;

public class ArticleCollectQueryVo {

    private Long total;

    private List<ArticleCollectQueryVoUnit> list;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<ArticleCollectQueryVoUnit> getList() {
        return list;
    }

    public void setList(List<ArticleCollectQueryVoUnit> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ArticleCollectQueryVo{" +
                "total=" + total +
                ", list=" + list +
                '}';
    }
}
