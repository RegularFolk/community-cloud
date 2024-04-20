package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.SysNotifyDto;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.RemoteNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteNotifyFallbackFactory implements FallbackFactory<RemoteNotifyService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteNotifyFallbackFactory.class);

    @Override
    public RemoteNotifyService create(Throwable throwable) {
        log.error("通知服务调用失败:{}", throwable.getMessage());
        return new RemoteNotifyService() {
            @Override
            public AjaxResult systemNotifyBatch(SysNotifyDto dto) {
                return AjaxResult.error("调用系统通知失败！" + throwable.getMessage());
            }
        };
    }
}
