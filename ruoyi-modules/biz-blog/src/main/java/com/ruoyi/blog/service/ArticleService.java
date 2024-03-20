package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.dto.*;
import com.ruoyi.blog.domain.vo.*;
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

    ArticleVo getArticle(Long articleId);

    int collect(IdDto dto);

    ArticleCollectQueryVo collectList(ArticleCollectQueryDto dto);

    int cancelCollect(IdDto dto);

    PersonArticleVo getPersonalArticle(PersonArticleDto dto);

    int addPersonClass(Long userId, String className);

    List<ArticleSquareVo> getArticleSquareList(ArticleQueryDto dto);

    List<BlogRankVo> getViewRank();

}
