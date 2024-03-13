package com.ruoyi.blog.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogLikedMapper {
    int insertLike(@Param("userId") Long userId, @Param("blogId") Long blogId);

    int deleteLike(@Param("userId") Long userId, @Param("blogId") Long blogId);

    int isLiked(@Param("userId") Long userId, @Param("articleId") Long articleId);

    List<Long> selectLikedIds(@Param("articleIdList") List<Long> articleIdList, @Param("userId") String userId);
}
