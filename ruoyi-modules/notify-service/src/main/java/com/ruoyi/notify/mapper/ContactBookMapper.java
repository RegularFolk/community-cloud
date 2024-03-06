package com.ruoyi.notify.mapper;

import com.ruoyi.notify.domain.ContactBook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContactBookMapper {
    List<ContactBook> selectContactList(
            @Param("contactBook") ContactBook contactBook,
            @Param("pageSize") Integer pageSize,
            @Param("offset") Integer offset);

    int insertContact(ContactBook contactBook);

    int updateContactTime(ContactBook contactBook);

    int getTotal(@Param("userId") Long userId);
}
