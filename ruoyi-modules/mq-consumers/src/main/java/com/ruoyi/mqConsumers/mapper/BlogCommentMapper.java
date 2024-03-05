package com.ruoyi.mqConsumers.mapper;

import org.apache.ibatis.annotations.Param;

public interface BlogCommentMapper {
    int increaseLikeCnt(Long commentId);

    int decreaseLikeCnt(Long commentId);

    int updateLikeCnt(@Param("commentId") Long commentId, @Param("type") Integer type);
}
