package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.ListDto;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.sql.SqlUtil;
import com.ruoyi.common.mq.callBack.DefaultCallBack;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.user.UserFollowMessage;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.domain.BizUser;
import com.ruoyi.system.domain.dto.FollowDto;
import com.ruoyi.system.domain.vo.BizUserVo;
import com.ruoyi.system.mapper.BizUserMapper;
import com.ruoyi.system.mapper.FollowListMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.UserFollowService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserFollowServiceImpl implements UserFollowService {

    @Resource
    private FollowListMapper followListMapper;

    @Resource
    private BizUserMapper bizUserMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RocketMQTemplate rocketmqTemplate;

    @Override
    public int follow(FollowDto dto) {
        Long followId = dto.getFollowId();
        Long userId = SecurityUtils.getUserId();
        OperateType typeEnum = OperateType.getEnum(dto.getOperateType());
        if (followId.equals(userId)) {
            throw new ServiceException("不可以关注自己！");
        }

        int flag = 0;
        switch (typeEnum) {
            case ADD:
                flag = followListMapper.addFollowList(userId, followId);
                if (flag > 0) {
                    bizUserMapper.increaseSubCnt(userId);
                }
                break;
            case CANCEL:
                flag = followListMapper.delFollowList(userId, followId);
                if (flag > 0) {
                    bizUserMapper.decreaseSubCnt(userId);
                }
                break;
            default:
                // unreachable
                break;
        }


        if (flag > 0) {
            UserFollowMessage message = new UserFollowMessage();
            message.setMessageId(dto.getFollowId());
            message.setUserId(dto.getFollowId());
            message.setOperateType(typeEnum.getType());
            // 发送消息异步更新用户字段
            rocketmqTemplate.asyncSendOrderly(
                    MqTopicConstants.USER_FOLLOW_TOPIC,
                    message,
                    String.valueOf(message.getMessageId()),
                    new DefaultCallBack<>(this.getClass(), message)
            );
        }

        return flag;
    }

    @Override
    public BizUser getBizUser(IdDto dto) {
        return bizUserMapper.getById(dto.getId());
    }

    @Override
    public Boolean isFollowed(IdDto dto) {
        Long userId = SecurityUtils.getUserId();
        int flag = followListMapper.isFollowed(userId, dto.getId());
        return flag > 0;
    }

    @Override
    public List<BizUserVo> getFollowList(ListDto dto) {
        // 获取粉丝列表
        Long userId = SecurityUtils.getUserId();
        List<Long> idList = followListMapper.getFollowerIdList(
                userId,
                dto.getPageSize(),
                SqlUtil.getOffset(dto.getPageNum(), dto.getPageSize()));
        return packBizUserVoList(userId, idList);
    }

    @Override
    public List<BizUserVo> getSubList(ListDto dto) {
        // 获取关注列表
        Long userId = SecurityUtils.getUserId();
        List<Long> idList = followListMapper.getSubIdList(
                userId,
                dto.getPageSize(),
                SqlUtil.getOffset(dto.getPageNum(), dto.getPageSize())
        );

        return packBizUserVoList(userId, idList);


    }

    private List<BizUserVo> packBizUserVoList(Long userId, List<Long> idList) {
        List<SysUser> sysUsers = sysUserMapper.selectUserByIds(idList);

        List<BizUser> bizUserList = bizUserMapper.getByIdList(idList);

        Map<Long, List<BizUser>> bizUserGroup = bizUserList
                .stream().collect(Collectors.groupingBy(BizUser::getUserId));


        List<Long> isFollowedList = followListMapper.getIsFollowedList(userId, idList);
        Set<Long> isFollowedSet = new HashSet<>(isFollowedList);

        return sysUsers.stream().map(s -> {
            BizUserVo vo = new BizUserVo();
            BizUser bizUser = bizUserGroup.get(s.getUserId()).get(0);
            vo.setUserId(s.getUserId());
            vo.setUserName(s.getUserName());
            vo.setAvatar(s.getAvatar());
            vo.setFollowerCnt(bizUser.getFollowerCnt());
            vo.setLikeCnt(bizUser.getLikeCnt());
            vo.setSubCnt(bizUser.getSubCnt());
            vo.setFollowed(isFollowedSet.contains(s.getUserId()));
            return vo;
        }).collect(Collectors.toList());
    }
}
