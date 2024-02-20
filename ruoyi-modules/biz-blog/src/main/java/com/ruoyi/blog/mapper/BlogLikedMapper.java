package com.ruoyi.blog.mapper;

import org.apache.ibatis.annotations.Param;

public interface BlogLikedMapper {
    int insertLike(@Param("userId") Long userId, @Param("blogId") Long blogId);

    int deleteLike(@Param("userId") Long userId, @Param("blogId") Long blogId);

    int isLiked(@Param("userId") Long userId, @Param("articleId") Long articleId);
}
