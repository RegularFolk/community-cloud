package com.ruoyi.blog.service.impl;

import com.ruoyi.blog.domain.BlogAccess;
import com.ruoyi.blog.domain.dto.AccessListDto;
import com.ruoyi.blog.domain.dto.ChangeAccessStatusDto;
import com.ruoyi.blog.domain.vo.AccessGenerateVo;
import com.ruoyi.blog.enums.AccessStatusEnum;
import com.ruoyi.blog.mapper.BlogAccessMapper;
import com.ruoyi.blog.service.AccessService;
import com.ruoyi.common.core.domain.ListDto;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.sql.SqlUtil;
import com.ruoyi.common.core.utils.uuid.IdUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AccessServiceImpl implements AccessService {

    @Resource
    private BlogAccessMapper accessMapper;

    @Override
    public AccessGenerateVo generate(String phone) {
        int cnt = accessMapper.getAccessByPhone(phone);

        if (cnt > 0) {
            return null;
        }

        String keyId = IdUtils.fastSimpleUUID();
        String keySecret = IdUtils.fastSimpleUUID();

        cnt = accessMapper.insertAccess(phone, keyId, keySecret, DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));

        return cnt > 0 ? new AccessGenerateVo(phone, keyId, keySecret) : null;
    }

    @Override
    public List<BlogAccess> list(AccessListDto dto) {
        BlogAccess access = new BlogAccess();
        if (StringUtils.isNotEmpty(dto.getPhoneNumber())) {
            access.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getStatus() != null && dto.getStatus() != 0) {
            access.setStatus(dto.getStatus());
        }
        return accessMapper.list(access, dto.getPageSize(), SqlUtil.getOffset(dto.getPageNum(), dto.getPageSize()));
    }

    @Override
    public int getTotal(AccessListDto dto) {
        BlogAccess access = new BlogAccess();
        if (StringUtils.isNotEmpty(dto.getPhoneNumber())) {
            access.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getStatus() != null && dto.getStatus() != 0) {
            access.setStatus(dto.getStatus());
        }
        return accessMapper.getTotal(access);
    }

    @Override
    public int changeStatus(ChangeAccessStatusDto dto) {
        AccessStatusEnum accessStatus = AccessStatusEnum.getEnum(dto.getStatus());
        int flag = 0;
        switch (accessStatus) {
            case ENABLE:
                flag = accessMapper.enable(dto.getPhoneNumber());
                break;
            case DISABLE:
                flag = accessMapper.disable(dto.getPhoneNumber());
                break;
            default:
                // unreachable
                break;
        }
        return flag;
    }

    @Override
    public int delete(String phoneNumber) {
        return accessMapper.delete(phoneNumber);
    }


}
