package com.ruoyi.file.demo;


import com.aliyun.tea.*;
import com.aliyun.teaconsole.*;
import com.aliyun.teautil.*;
import com.aliyun.darabonba.env.*;
import com.aliyun.vod20170321.*;
import com.aliyun.vod20170321.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;

public class Sample {

    public static com.aliyun.vod20170321.Client Initialization(String regionId) throws Exception {
        Config config = new Config();
        // 您的AccessKey ID
        config.accessKeyId = com.aliyun.darabonba.env.EnvClient.getEnv("ACCESS_KEY_ID");
        // 您的AccessKey Secret
        config.accessKeySecret = com.aliyun.darabonba.env.EnvClient.getEnv("ACCESS_KEY_SECRET");
        // 您的可用区ID
        config.regionId = regionId;
        return new com.aliyun.vod20170321.Client(config);
    }

    public static GetPlayInfoResponse GetPlayInfoSample(com.aliyun.vod20170321.Client client, String videoId) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        // 视频ID。
        request.videoId = videoId;
        GetPlayInfoResponse response = client.getPlayInfo(request);
        return response;
    }

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        try {
            String regionId = args.get(0);
            String videoId = args.get(1);
            com.aliyun.vod20170321.Client client = Sample.Initialization(regionId);
            GetPlayInfoResponse responseGetPlayInfo = Sample.GetPlayInfoSample(client, videoId);
            com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(com.aliyun.teautil.Common.toMap(responseGetPlayInfo)));
        } catch (TeaException error) {
            com.aliyun.teaconsole.Client.log(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            com.aliyun.teaconsole.Client.log(error.message);
        }
    }
}


