package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.dto.ArticleQueryDto;
import com.ruoyi.blog.domain.dto.DeletePersonClassDto;
import com.ruoyi.blog.domain.dto.PostArticleClassDto;
import com.ruoyi.blog.domain.dto.PostArticleDto;
import com.ruoyi.blog.domain.vo.ArticleQueryVo;
import com.ruoyi.blog.domain.vo.PersonClassVo;
import com.ruoyi.common.core.domain.IdDto;

import java.util.List;

public interface ArticleService {

    Long postArticle(PostArticleDto dto);

    int postPersonClassification(PostArticleClassDto dto);

    int deletePersonClass(DeletePersonClassDto id);

    int deleteArticle(IdDto id);

    List<ArticleQueryVo> getArticleList(ArticleQueryDto dto);

    List<PersonClassVo> getPersonClassList(Long userId);

    long getTotalArticle(ArticleQueryDto dto);
}
