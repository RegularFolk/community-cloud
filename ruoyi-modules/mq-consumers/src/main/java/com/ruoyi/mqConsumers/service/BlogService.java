package com.ruoyi.mqConsumers.service;

import com.ruoyi.common.mq.enums.OperateType;

public interface BlogService {

    int addCommentCnt(Long blogId);

    int addViewCnt(Long blogId);

    int changeCollectCnt(Long blogId, OperateType typeEnum);

    int changeLikeCnt(Long blogId, OperateType typeEnum);
}
