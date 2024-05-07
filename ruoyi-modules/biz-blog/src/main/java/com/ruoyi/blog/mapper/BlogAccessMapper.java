package com.ruoyi.blog.mapper;

import com.ruoyi.blog.domain.BlogAccess;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogAccessMapper {
    int getAccessByPhone(String phone);

    int insertAccess(
            @Param("phone") String phone,
            @Param("keyId") String keyId,
            @Param("keySecret") String keySecret,
            @Param("lastUseTime") String lastUseTime);

    List<BlogAccess> list(
            @Param("access") BlogAccess access,
            @Param("pageSize") Integer pageSize,
            @Param("offset") int offset);

    int getTotal(@Param("access") BlogAccess access);

    int enable(String phoneNumber);

    int disable(String phoneNumber);

    int delete(String phoneNumber);
}
