package com.ruoyi.file.service;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod20170321.Client;
import com.aliyun.vod20170321.models.GetPlayInfoRequest;
import com.aliyun.vod20170321.models.GetPlayInfoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.file.config.AliVodConfig;
import com.ruoyi.file.utils.AliyunVodSDKUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.UUID;

@Component
public class VodServiceImpl implements VodService {

    @Resource
    private AliVodConfig aliVodConfig;

    /**
     * 上传网络流的方式上传文件
     *
     * @param file
     * @return
     */
    @Override
    public String uploadVod(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            String filename = file.getOriginalFilename();
            String title = filename.substring(0, filename.lastIndexOf('.')) + "==>" + UUID.randomUUID();
            UploadStreamRequest request = new UploadStreamRequest(
                    aliVodConfig.getAccessKeyId(),
                    aliVodConfig.getAccessKeySecret(),
                    title,
                    filename,
                    inputStream);
            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(request);
            return response.getVideoId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("上传视频失败！");
        }
    }

    @Override
    public String getVodUrl(String vodId) {
        try {
            Client client = AliyunVodSDKUtils.initVodAsyncClient(
                    aliVodConfig.getAccessKeyId(),
                    aliVodConfig.getAccessKeySecret());
            GetPlayInfoRequest request = new GetPlayInfoRequest();
            request.videoId = vodId;
            GetPlayInfoResponse response = client.getPlayInfo(request);
            System.out.println("response.getBody() = " + response.getBody());
            return response.getBody().getPlayInfoList().getPlayInfo().get(0).getPlayURL();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("获取视频失败！");
        }
    }

    @Override
    public int delVod(String vodId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    aliVodConfig.getAccessKeyId(),
                    aliVodConfig.getAccessKeySecret());
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(vodId);//可以传入多个id，用逗号分隔
            client.getAcsResponse(request);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("删除视频失败！");
        }
    }


}
