package com.ruoyi.mqConsumers.service.impl;

import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.mqConsumers.mapper.BlogMapper;
import com.ruoyi.mqConsumers.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Override
    public int addCommentCnt(Long blogId) {
        return blogMapper.increaseCommentCnt(blogId);
    }

    @Override
    public int addViewCnt(Long blogId) {
        return blogMapper.increaseViewCnt(blogId);
    }

    @Override
    public int changeCollectCnt(Long blogId, OperateType typeEnum) {
        int flag = 0;
        switch (typeEnum) {
            case ADD:
                flag = blogMapper.increaseCollectCnt(blogId);
                break;
            case CANCEL:
                flag = blogMapper.decreaseCollectCnt(blogId);
                break;
            default:
                // unreachable
                break;
        }
        return flag;
    }

    @Override
    public int changeLikeCnt(Long blogId, OperateType typeEnum) {
        int flag = 0;
        switch (typeEnum) {
            case ADD:
                flag = blogMapper.increaseLikeCnt(blogId);
                break;
            case CANCEL:
                flag = blogMapper.decreaseLikeCnt(blogId);
                break;
            default:
                // unreachable
                break;
        }

        return flag;
    }

}
