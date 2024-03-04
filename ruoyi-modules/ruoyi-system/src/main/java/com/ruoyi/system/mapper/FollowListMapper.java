package com.ruoyi.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户关注表 biz_follow_list
 */
public interface FollowListMapper {
    int addFollowList(@Param("userId") Long userId, @Param("followId") Long followId);

    int delFollowList(@Param("userId") Long userId, @Param("followId") Long followId);

    int isFollowed(@Param("userId") Long userId, @Param("followerId") Long followerId);

    List<Long> getFollowerIdList(
            @Param("userId") Long userId,
            @Param("pageSize") Integer pageSize,
            @Param("offset") Integer offset);

    List<Long> getSubIdList(
            @Param("userId") Long userId,
            @Param("pageSize") Integer pageSize,
            @Param("offset") Integer offset);

    /**
     * 判断idList对应的用户是否已被userId关注
     */
    List<Long> getIsFollowedList(@Param("userId") Long userId, @Param("idList") List<Long> idList);
}
