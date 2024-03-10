package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.ListDto;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.domain.BizUser;
import com.ruoyi.system.domain.dto.FollowDto;
import com.ruoyi.system.domain.vo.BizUserVo;
import com.ruoyi.system.domain.vo.UserBasicInfoVo;
import com.ruoyi.system.service.UserFollowService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户关注Controller
 */
@RestController
@RequestMapping("/follow")
public class SysUserFollowController {

    @Resource
    private UserFollowService userFollowService;

    /**
     * 关注用户/取消关注
     */
    @PostMapping("/followUser")
    public AjaxResult followUser(@RequestBody FollowDto dto) {
        int flag = userFollowService.follow(dto);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error("操作失败，请稍后重试！");
    }

    /**
     * 获取用户粉丝数、关注数
     */
    @PostMapping("/getBizUser")
    public AjaxResult getBizUser(@RequestBody IdDto dto) {
        BizUser bizUser = userFollowService.getBizUser(dto);
        return AjaxResult.success(bizUser);
    }

    /**
     * 判断是否已关注
     */
    @PostMapping("/isFollowed")
    public AjaxResult isFollowed(@RequestBody IdDto dto) {
        Boolean flag = userFollowService.isFollowed(dto);
        return AjaxResult.success(flag);
    }

    /**
     * 获取粉丝列表
     */
    @PostMapping("/followerList")
    public AjaxResult followerList(@RequestBody ListDto dto) {
        List<UserBasicInfoVo> userList = userFollowService.getFollowList(dto);
        IdDto idDto = new IdDto();
        idDto.setId(SecurityUtils.getUserId());
        BizUser bizUser = userFollowService.getBizUser(idDto);
        AjaxResult ajaxResult = AjaxResult.success(userList);
        ajaxResult.put("total", bizUser.getFollowerCnt());
        return ajaxResult;
    }

    /**
     * 获取关注列表
     */
    @PostMapping("/subList")
    public AjaxResult subList(@RequestBody ListDto dto) {
        List<UserBasicInfoVo> userList = userFollowService.getSubList(dto);
        IdDto idDto = new IdDto();
        idDto.setId(SecurityUtils.getUserId());
        BizUser bizUser = userFollowService.getBizUser(idDto);
        AjaxResult ajaxResult = AjaxResult.success(userList);
        ajaxResult.put("total", bizUser.getSubCnt());
        return ajaxResult;
    }




}
