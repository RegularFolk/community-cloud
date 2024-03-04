package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BizUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户业务信息表 biz_user
 */
public interface BizUserMapper {
    int increaseSubCnt(@Param("userId") Long userId);

    int decreaseSubCnt(@Param("userId") Long userId);

    BizUser getById(@Param("id") Long id);

    List<BizUser> getByIdList(@Param("idList") List<Long> idList);
}
