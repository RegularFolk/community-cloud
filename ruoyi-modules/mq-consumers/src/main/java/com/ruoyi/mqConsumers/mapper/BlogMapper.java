package com.ruoyi.mqConsumers.mapper;

public interface BlogMapper {


    int increaseCommentCnt(Long blogId);

    int increaseViewCnt(Long blogId);

    int increaseCollectCnt(Long blogId);

    int decreaseCollectCnt(Long blogId);

    int increaseLikeCnt(Long blogId);

    int decreaseLikeCnt(Long blogId);
}
