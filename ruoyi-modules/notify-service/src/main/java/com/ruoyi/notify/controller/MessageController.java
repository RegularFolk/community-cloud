package com.ruoyi.notify.controller;

import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.ListDto;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.notify.domain.TextMessage;
import com.ruoyi.notify.domain.dto.MsgListDto;
import com.ruoyi.notify.domain.dto.SendDto;
import com.ruoyi.notify.domain.vo.ContactListVo;
import com.ruoyi.notify.service.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 发送消息
     */
    @PostMapping("/send")
    public AjaxResult send(@RequestBody SendDto dto) {
        int flag = messageService.send(dto);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error("发送失败！请稍后重试或者联系管理员");
    }

    /**
     * 获取联系人列表
     */
    @PostMapping("/contactList")
    public AjaxResult contactList(@RequestBody ListDto dto) {
        List<ContactListVo> voList = messageService.contactList(dto);
        int total = messageService.getTotalContactCnt();
        AjaxResult ajaxResult = AjaxResult.success(voList);
        ajaxResult.put("total", total);
        return ajaxResult;
    }

    /**
     * 查询消息记录
     */
    @PostMapping("/msgList")
    public AjaxResult msgList(@RequestBody MsgListDto dto) {
        List<TextMessage> messageList = messageService.msgList(dto);
        return AjaxResult.success(messageList);
    }

    /**
     * 在线聊天前端轮询接口
     */
    @PostMapping("/pullMsg")
    public AjaxResult pullMsg(@RequestBody IdDto dto) {
        List<TextMessage> messageList = messageService.pullMsg(dto);
        return AjaxResult.success(messageList);
    }







}
