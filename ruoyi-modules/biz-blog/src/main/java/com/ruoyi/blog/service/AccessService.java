package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.BlogAccess;
import com.ruoyi.blog.domain.dto.AccessListDto;
import com.ruoyi.blog.domain.dto.ChangeAccessStatusDto;
import com.ruoyi.blog.domain.vo.AccessGenerateVo;
import com.ruoyi.common.core.domain.ListDto;

import java.util.List;

public interface AccessService {
    AccessGenerateVo generate(String phone);

    List<BlogAccess> list(AccessListDto dto);

    int getTotal(AccessListDto dto);

    int changeStatus(ChangeAccessStatusDto dto);

    int delete(String phoneNumber);
}
