package com.ruoyi.mqConsumers.mapper;

import org.apache.ibatis.annotations.Param;

public interface BlogMapper {


    int increaseCommentCnt(Long blogId);

    int increaseViewCnt(Long blogId);

    int increaseCollectCnt(Long blogId);

    int decreaseCollectCnt(Long blogId);

    int increaseLikeCnt(Long blogId);

    int decreaseLikeCnt(Long blogId);

    int updateLikeCnt(@Param("blogId") Long blogId, @Param("type") Integer type);

    int updateViewCnt(@Param("blogId") Long blogId, @Param("type") Integer type);

    int updateCommentCnt(@Param("blogId") Long blogId, @Param("type") Integer type);

    int updateCollectCnt(@Param("blogId") Long blogId, @Param("type") Integer type);
}
