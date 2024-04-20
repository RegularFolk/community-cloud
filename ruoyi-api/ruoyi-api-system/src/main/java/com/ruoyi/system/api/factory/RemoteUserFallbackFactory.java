package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.ListDto;
import com.ruoyi.common.core.domain.UserBasicInfoVo;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;

import java.util.List;

/**
 * 用户服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteUserService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserService()
        {
            @Override
            public R<LoginUser> getUserInfo(String username, String source)
            {
                return R.fail("获取用户失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> registerUserInfo(SysUser sysUser, String source)
            {
                return R.fail("注册用户失败:" + throwable.getMessage());
            }

            @Override
            public R<List<SysUser>> getInfoByIds(List<Long> ids, String source) {
                return R.fail("获取用户列表失败:" + throwable.getMessage());
            }

            @Override
            public R<List<UserBasicInfoVo>> getUserBasicInfoByIds(List<Long> ids, String source) {
                return R.fail("获取用户基本信息列表失败:" + throwable.getMessage());
            }

            @Override
            public AjaxResult getUserFollowerList(ListDto dto) {
                return AjaxResult.error(AjaxResult.DEFAULT_ERROR_MSG);
            }


        };
    }
}
