package com.ruoyi.blog.api.factory;

import com.ruoyi.blog.api.RemoteBlogService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteBlogFallbackFactory implements FallbackFactory<RemoteBlogService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteBlogFallbackFactory.class);

    @Override
    public RemoteBlogService create(Throwable throwable) {
        log.error("blog服务调用失败:{}", throwable.getMessage());

        return new RemoteBlogService() {

            @Override
            public AjaxResult addPersonClass(Long userId, String className, String source) {
                return AjaxResult.error("新增个人分类失败：" + throwable.getMessage());
            }
        };
    }
}
