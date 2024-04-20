package com.ruoyi.file.controller;

import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.file.FileUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.file.service.ISysFileService;
import com.ruoyi.file.service.VodService;
import com.ruoyi.system.api.domain.SysFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件请求处理
 *
 * @author ruoyi
 */
@RestController
public class SysFileController {
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Autowired
    private ISysFileService sysFileService;

    @Resource
    private VodService vodService;

    /**
     * 文件上传请求
     */
    @PostMapping("upload")
    public R<SysFile> upload(MultipartFile file) {
        try {
            // 上传并返回访问地址
            String url = sysFileService.uploadFile(file);
            SysFile sysFile = new SysFile();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setUrl(url);
            return R.ok(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 视频上传
     *
     * @param file
     * @return
     */
    @PostMapping("vodUpload")
    public AjaxResult vodUpload(MultipartFile file) {
        // 上传并返回视频Id
        String videoId = vodService.uploadVod(file);
        return AjaxResult.success(videoId);
    }

    /**
     * 根据视频Id获取视频播放地址
     *
     * @param vodId
     * @return
     */
    @PostMapping("getVodUrl/{vodId}")
    public AjaxResult getVodUrl(@PathVariable("vodId") String vodId) {
        String url = vodService.getVodUrl(vodId);
        return StringUtils.isNotEmpty(url) ? AjaxResult.success(url) : AjaxResult.error("获取视频失败！");
    }

    /**
     * 根据视频Id删除视频
     *
     * @param vodId
     * @return
     */
    @PostMapping("/delVod/{vodId}")
    public AjaxResult delVod(@PathVariable("vodId") String vodId) {
        int flag = vodService.delVod(vodId);
        return flag > 0 ? AjaxResult.success() : AjaxResult.error("删除失败！请稍后再试或者联系管理员！");
    }

}