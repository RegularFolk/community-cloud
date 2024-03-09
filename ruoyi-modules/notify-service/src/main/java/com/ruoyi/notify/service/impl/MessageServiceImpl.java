package com.ruoyi.notify.service.impl;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.ListDto;
import com.ruoyi.common.core.utils.sql.SqlUtil;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.notify.constants.MessageConfigConstants;
import com.ruoyi.notify.domain.ContactBook;
import com.ruoyi.notify.domain.TextMessage;
import com.ruoyi.notify.domain.dto.MsgListDto;
import com.ruoyi.notify.domain.dto.SendDto;
import com.ruoyi.notify.domain.mapper.UnReadCnt;
import com.ruoyi.notify.domain.vo.ContactListVo;
import com.ruoyi.notify.mapper.ContactBookMapper;
import com.ruoyi.notify.mapper.TextMessageMapper;
import com.ruoyi.notify.service.MessageService;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private ContactBookMapper contactBookMapper;

    @Resource
    private TextMessageMapper textMessageMapper;

    @Resource
    private RemoteUserService remoteUserService;

    @Override
    public long send(SendDto dto) {
        Long senderId = SecurityUtils.getUserId();
        TextMessage message = new TextMessage();
        message.setSenderId(senderId);
        message.setReceiverId(dto.getReceiverId());
        message.setContent(dto.getContent());

        int flag = textMessageMapper.insertMessage(message);

        if (flag > 0) {
            flag = getAndUpdateContact(dto.getReceiverId(), senderId, dto.getContent());
            if (flag > 0) {
                getAndUpdateContact(senderId, dto.getReceiverId(), dto.getContent());
            }
        }

        return message.getId();
    }

    private int getAndUpdateContact(Long receiverId, Long senderId, String content) {
        int flag;
        // 添加联系人，如果已经是联系人，更新时间；注意，联系人是双向的
        ContactBook contactBook = new ContactBook();
        contactBook.setUserId(senderId);
        contactBook.setContactId(receiverId);
        contactBook.setMsgPreview(content.length() > MessageConfigConstants.MSG_PREVIEW_LIMIT ?
                content.substring(0, MessageConfigConstants.MSG_PREVIEW_LIMIT) : content);
        contactBook.setMsgPreview(contactBook.getMsgPreview() + "......");
        List<ContactBook> contact = contactBookMapper.selectContactList(contactBook, null, null);

        if (CollectionUtils.isEmpty(contact)) {
            flag = contactBookMapper.insertContact(contactBook);
        } else {
            contactBook.setCreateTime(new Date());
            flag = contactBookMapper.updateContact(contactBook);
        }
        return flag;
    }

    @Override
    public List<ContactListVo> contactList(ListDto dto) {
        Long userId = SecurityUtils.getUserId();
        ContactBook contactBook = new ContactBook();
        contactBook.setUserId(userId);

        List<ContactBook> contactBookList = contactBookMapper.selectContactList(
                contactBook,
                dto.getPageSize(),
                SqlUtil.getOffset(dto.getPageNum(), dto.getPageSize()));

        if (CollectionUtils.isEmpty(contactBookList)) {
            return new ArrayList<>();
        }

        List<Long> contactIdList = contactBookList.stream().map(ContactBook::getContactId).collect(Collectors.toList());
        List<SysUser> userList = remoteUserService.getInfoByIds(contactIdList, SecurityConstants.INNER).getData();
        Map<Long, List<SysUser>> groupById = userList.stream().collect(Collectors.groupingBy(SysUser::getUserId));

        // 查询与每一个联系人的未读消息数
        List<UnReadCnt> unReadCntList = textMessageMapper.selectUnReadCnt(userId, contactIdList);
        Map<Long, List<UnReadCnt>> unReadCntMap = unReadCntList.stream().collect(Collectors.groupingBy(UnReadCnt::getSenderId));

        // 查询与每个联系人最新一条消息的前15个字
        Map<Long, List<ContactBook>> contactGroupById = contactBookList
                .stream().collect(Collectors.groupingBy(ContactBook::getContactId));

        return contactBookList.stream().map(book -> {
            SysUser sysUser = groupById.get(book.getContactId()).get(0);
//            Long unreadCnt = unReadCntGroupByRcvId.get(sysUser.getUserId()).get(0).get(ColumnConstants.UNREAD_CNT);
            List<UnReadCnt> unReadCnts = unReadCntMap.get(book.getContactId());
            Long unreadCnt = CollectionUtils.isEmpty(unReadCnts) ? 0L : unReadCnts.get(0).getUnreadCnt();
            String msgPreview = contactGroupById.get(book.getContactId()).get(0).getMsgPreview();
            ContactListVo vo = new ContactListVo();
            vo.setContactId(sysUser.getUserId());
            vo.setContactName(sysUser.getNickName());
            vo.setAvatar(sysUser.getAvatar());
            vo.setUnReadCnt(unreadCnt);
            vo.setLatestMsgPreview(msgPreview);

            return vo;
        }).collect(Collectors.toList());

    }

    @Override
    public int getTotalContactCnt() {
        Long userId = SecurityUtils.getUserId();
        return contactBookMapper.getTotal(userId);
    }

    @Override
    public List<TextMessage> msgList(MsgListDto dto) {
        // 判断未读消息数，如果未读消息数大于pageSize，按照未读消息数查询，同时把所有未读消息设置为已读消息
        Long userId = SecurityUtils.getUserId();

        List<UnReadCnt> unReadCntList = textMessageMapper.selectUnReadCnt(userId, Collections.singletonList(dto.getContactId()));
        long unreadCnt = CollectionUtils.isEmpty(unReadCntList) ? 0L : unReadCntList.get(0).getUnreadCnt();

        if (unreadCnt > dto.getPageSize()) {
            dto.setPageSize((int) unreadCnt);
        }

        if (unreadCnt > 0) {
            // 设置消息为已读
            textMessageMapper.updateHasReadMsg(userId, dto.getContactId());
        }

        List<TextMessage> messageList = textMessageMapper.selectMsgList(
                userId,
                dto.getContactId(),
                dto.getStartMsgId(),
                dto.getPageSize());

        Collections.reverse(messageList);

        return messageList;
    }

    @Override
    public List<TextMessage> pullMsg(IdDto dto) {
        Long userId = SecurityUtils.getUserId();
        Long contactId = dto.getId();
        List<TextMessage> msgList = textMessageMapper.selectUnReadMsg(userId, contactId);

        if (CollectionUtils.isEmpty(msgList)) {
            return new ArrayList<>();
        }

        textMessageMapper.updateHasReadMsg(userId, contactId);

        return msgList;
    }


}
