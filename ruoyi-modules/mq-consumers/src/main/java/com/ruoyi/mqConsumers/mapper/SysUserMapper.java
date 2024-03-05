package com.ruoyi.mqConsumers.mapper;

import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    int increaseFollowCnt(@Param("userId") Long userId);

    int decreaseFollowCnt(@Param("userId") Long userId);

    int changeFollowCnt(@Param("userId") Long userId, @Param("type") Integer type);
}
