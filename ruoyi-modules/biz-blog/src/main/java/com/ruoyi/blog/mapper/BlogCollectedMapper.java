package com.ruoyi.blog.mapper;

import com.ruoyi.blog.domain.BlogCollected;
import com.ruoyi.blog.domain.dto.ArticleCollectQueryDto;
import com.ruoyi.blog.domain.vo.ArticleCollectQueryVoUnit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogCollectedMapper {

    int insertBlogCollected(BlogCollected blogCollected);

    List<ArticleCollectQueryVoUnit> collectList(@Param("userId") Long userId,
                                                @Param("dto") ArticleCollectQueryDto dto,
                                                @Param("pageSize") Integer pageSize,
                                                @Param("offset") Integer offset);

    long getCollectedTotal(Long userId);

    int deleteCollect(@Param("blogId") Long blogId, @Param("userId") Long userId);

    int isCollected(@Param("articleId") Long articleId, @Param("userId") Long userId);
}
