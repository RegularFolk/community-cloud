package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.SysNotifyDto;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.factory.RemoteNotifyFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId = "remoteNotifyService", value = ServiceNameConstants.NOTIFY_SERVICE, fallbackFactory = RemoteNotifyFallbackFactory.class)
public interface RemoteNotifyService {

    @PostMapping("/message/systemNotifyBatch")
    public AjaxResult systemNotifyBatch(@RequestBody SysNotifyDto dto);

}
