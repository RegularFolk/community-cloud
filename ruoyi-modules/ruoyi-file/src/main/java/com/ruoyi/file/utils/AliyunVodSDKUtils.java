package com.ruoyi.file.utils;

import com.aliyun.darabonba.env.EnvClient;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.vod20170321.Client;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

public class AliyunVodSDKUtils {
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }

    public static Client initVodAsyncClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config();
        // 您的AccessKey ID
        config.accessKeyId = accessKeyId;
        // 您的AccessKey Secret
        config.accessKeySecret = accessKeySecret;
        // 您的可用区ID
        config.regionId = "cn-shanghai";
        return new Client(config);
    }
}
