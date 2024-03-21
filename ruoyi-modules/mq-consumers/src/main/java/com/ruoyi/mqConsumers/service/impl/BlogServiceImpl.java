package com.ruoyi.mqConsumers.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.mq.domain.BlogMessage;
import com.ruoyi.common.mq.enums.BlogTypeEnum;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.common.redis.constants.RedisPrefix;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.mqConsumers.mapper.BlogMapper;
import com.ruoyi.mqConsumers.service.BlogService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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

    @Override
    public int handleBlogMessage(
            Long blogId,
            OperateType operateType,
            BlogMessage.MessageType messageType,
            BlogTypeEnum blogType) {

        int flag = 0;

        switch (messageType) {
            case LIKE:
                flag = blogMapper.updateLikeCnt(blogId, operateType.getType());
                if (OperateType.ADD.equals(operateType)) {
                    addLikeRank(blogId, blogType);
                }
                break;
            case VIEW:
                flag = blogMapper.updateViewCnt(blogId, operateType.getType());
                if (OperateType.ADD.equals(operateType)) {
                    addViewRank(blogId, blogType);
                }
                break;
            case COMMENT:
                flag = blogMapper.updateCommentCnt(blogId, operateType.getType());
                break;
            case COLLECT:
                flag = blogMapper.updateCollectCnt(blogId, operateType.getType());
                if (OperateType.ADD.equals(operateType)) {
                    addCollectRank(blogId, blogType);
                }
                break;
            default:
                // unreachable
                break;
        }

        return flag;
    }

    /**
     * Redis中更新收藏记录
     *
     * @param blogId
     * @param blogType
     */
    private void addCollectRank(Long blogId, BlogTypeEnum blogType) {
        String redisKey = RedisPrefix.ARTICLE_COLLECT + blogType.getType() + ":" + DateUtils.dateTime();
        expireKey(redisKey);
        redisTemplate.opsForZSet().incrementScore(redisKey, blogId, 1D);
    }

    /**
     * Redis中更新浏览记录
     *
     * @param blogId
     * @param blogType
     */
    private void addViewRank(Long blogId, BlogTypeEnum blogType) {
        String redisKey = RedisPrefix.ARTICLE_VIEW + blogType.getType() + ":" + DateUtils.dateTime();
        expireKey(redisKey);
        redisTemplate.opsForZSet().incrementScore(redisKey, blogId, 1D);
    }

    /**
     * Redis中更新点赞记录
     *
     * @param blogId
     * @param blogType
     */
    private void addLikeRank(Long blogId, BlogTypeEnum blogType) {
        String redisKey = RedisPrefix.ARTICLE_LIKE + blogType.getType() + ":" + DateUtils.dateTime();
        expireKey(redisKey);
        redisTemplate.opsForZSet().incrementScore(redisKey, blogId, 1D);
    }

    private void expireKey(String redisKey) {
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
            redisTemplate.opsForZSet().add(redisKey, 0L, 0D);
            redisTemplate.expire(redisKey, 10, TimeUnit.DAYS);
        }
    }


}
