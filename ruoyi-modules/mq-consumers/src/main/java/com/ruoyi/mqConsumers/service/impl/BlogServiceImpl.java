package com.ruoyi.mqConsumers.service.impl;

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

}
