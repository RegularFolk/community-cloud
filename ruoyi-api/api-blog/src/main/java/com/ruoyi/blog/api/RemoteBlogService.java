package com.ruoyi.blog.api;

import com.ruoyi.blog.api.factory.RemoteBlogFallbackFactory;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * blog服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteBlogService", value = ServiceNameConstants.BLOG_SERVICE, fallbackFactory = RemoteBlogFallbackFactory.class)
public interface RemoteBlogService {

    @PostMapping("/article/addPersonClass/{userId}/{className}")
    public AjaxResult addPersonClass(
            @PathVariable("userId") Long userId,
            @PathVariable("className") String className,
            @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
