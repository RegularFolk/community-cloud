package com.ruoyi.mqConsumers.mapper;

public interface BlogCommentMapper {
    int increaseLikeCnt(Long commentId);

    int decreaseLikeCnt(Long commentId);
}
