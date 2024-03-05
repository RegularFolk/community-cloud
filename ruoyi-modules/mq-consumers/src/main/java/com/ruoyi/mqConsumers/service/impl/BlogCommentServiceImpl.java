package com.ruoyi.mqConsumers.service.impl;

import com.ruoyi.common.mq.domain.CommentMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.mqConsumers.mapper.BlogCommentMapper;
import com.ruoyi.mqConsumers.service.BlogCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BlogCommentServiceImpl implements BlogCommentService {

    @Resource
    private BlogCommentMapper blogCommentMapper;

    @Override
    public int changeLikeCnt(Long commentId, OperateType typeEnum) {
        int flag = 0;
        switch (typeEnum) {
            case ADD:
                flag = blogCommentMapper.increaseLikeCnt(commentId);
                break;
            case CANCEL:
                flag = blogCommentMapper.decreaseLikeCnt(commentId);
                break;
            default:
                // unreachable
                break;
        }

        return flag;
    }

    @Override
    public int handleCommentMessage(Long commentId, OperateType operateType, CommentMessage.MessageType messageType) {
        int flag = 0;
        switch (messageType) {
            case LIKE:
                flag = blogCommentMapper.updateLikeCnt(commentId, operateType.getType());
                break;
            default:
                // unreachable
                break;
        }


        return flag;
    }
}
