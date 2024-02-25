package com.ruoyi.blog.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentLikedMapper {
    int isLiked(@Param("userId") Long userId, @Param("commentId") Long commentId);

    int insertLike(@Param("userId") Long userId, @Param("commentId") Long commentId);

    int deleteLike(@Param("userId") Long userId, @Param("commentId") Long commentId);

    List<Long> getLikedIds(@Param("userId") Long userId, @Param("commentIdList") List<Long> commentIdList);
}
