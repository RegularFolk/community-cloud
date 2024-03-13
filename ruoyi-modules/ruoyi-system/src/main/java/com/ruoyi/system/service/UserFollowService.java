package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.ListDto;
import com.ruoyi.system.domain.BizUser;
import com.ruoyi.system.domain.dto.FollowDto;
import com.ruoyi.common.core.domain.UserBasicInfoVo;

import java.util.List;

public interface UserFollowService {
    int follow(FollowDto dto);

    BizUser getBizUser(IdDto dto);

    Boolean isFollowed(IdDto dto);

    List<UserBasicInfoVo> getFollowList(ListDto dto);

    List<UserBasicInfoVo> getSubList(ListDto dto);

    public List<UserBasicInfoVo> packUserBasicInfoVoList(Long userId, List<Long> idList);
}
