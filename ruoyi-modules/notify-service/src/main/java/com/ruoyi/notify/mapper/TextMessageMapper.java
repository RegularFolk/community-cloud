package com.ruoyi.notify.mapper;

import com.ruoyi.notify.domain.TextMessage;
import com.ruoyi.notify.domain.mapper.UnReadCnt;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TextMessageMapper {
    int insertMessage(TextMessage message);

    List<UnReadCnt> selectUnReadCnt(
            @Param("userId") Long userId,
            @Param("contactIdList") List<Long> contactIdList);

    // 查询与每个联系人最新一条消息的前15个字
    //TODO 需要改造
    List<Map<String, Object>> selectMsgPrevView(
            @Param("userId") Long userId,
            @Param("contactIdList") List<Long> contactIdList);

    List<TextMessage> selectMsgList(
            @Param("userId") Long userId,
            @Param("contactId") Long contactId,
            @Param("startMsgId") Long startMsgId,
            @Param("pageSize") Integer pageSize);

    int updateHasReadMsg(
            @Param("receiverId") Long receiverId,
            @Param("senderId") Long senderId);

    List<TextMessage> selectUnReadMsg(
            @Param("receiverId") Long receiverId,
            @Param("senderId") Long senderId);
}
