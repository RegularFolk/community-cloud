package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.dto.ArticleQueryDto;
import com.ruoyi.blog.domain.dto.PostArticleClassDto;
import com.ruoyi.blog.domain.dto.PostArticleDto;
import com.ruoyi.blog.domain.vo.ArticleQueryVo;
import com.ruoyi.blog.domain.vo.IndexBlogVo;

import java.util.List;

public interface BlogService {
    List<IndexBlogVo> getRandomBlog();

    Long postArticle(PostArticleDto dto);

    Long postPersonClassification(PostArticleClassDto dto);

    int deletePersonClass(Long id);

    int deleteArticle(Long id);

    List<ArticleQueryVo> getArticleList(ArticleQueryDto dto);
}
