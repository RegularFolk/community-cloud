package com.ruoyi.notify.service;

import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.ListDto;
import com.ruoyi.notify.domain.TextMessage;
import com.ruoyi.notify.domain.dto.MsgListDto;
import com.ruoyi.notify.domain.dto.SendDto;
import com.ruoyi.notify.domain.vo.ContactListVo;

import java.util.List;

public interface MessageService {
    long send(SendDto dto);

    List<ContactListVo> contactList(ListDto dto);

    int getTotalContactCnt();

    List<TextMessage> msgList(MsgListDto dto);

    List<TextMessage> pullMsg(IdDto dto);
}
