package com.ruoyi.blog.service.impl;

import com.ruoyi.blog.service.MailService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.ListDto;
import com.ruoyi.common.core.domain.SysNotifyDto;
import com.ruoyi.common.core.domain.UserBasicInfoVo;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.sql.SqlUtil;
import com.ruoyi.system.api.RemoteNotifyService;
import com.ruoyi.system.api.RemoteUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableAsync
public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private RemoteUserService remoteUserService;

    @Resource
    private RemoteNotifyService remoteNotifyService;

    @Value("${mail.fromMail.addr}")
    private String from;

    @Override
    public void systemNotifyToFollowers(String subject, String content) {
        ListDto listDto = new ListDto();
        listDto.setPageSize(999);
        listDto.setPageNum(1);
        List<LinkedHashMap<String, Object>> followers = (List<LinkedHashMap<String, Object>>) remoteUserService.getUserFollowerList(listDto).get("data");
        List<Long> followerIds = followers.stream().map(item -> SqlUtil.cast2Long(item.get("id"))).collect(Collectors.toList());

        systemNotify(followerIds, subject, content);
    }

    /**
     * 系统通知，同时进行站内通知和站外通知
     * @param userIds
     * @param subject
     * @param content
     */
    @Async
    @Override
    public void systemNotify(List<Long> userIds, String subject, String content) {
        if (CollectionUtils.isEmpty(userIds) || StringUtils.isEmpty(content)) {
            return;
        }
        SysNotifyDto notifyDto = new SysNotifyDto();
        notifyDto.setUserIds(userIds);
        notifyDto.setContent(content);
        remoteNotifyService.systemNotifyBatch(notifyDto);

        List<UserBasicInfoVo> userInfos = remoteUserService.getUserBasicInfoByIds(userIds, SecurityConstants.INNER).getData();

        userInfos.forEach(user -> {
            if (StringUtils.isNotEmpty(user.getEmail())) {
                sendSimpleMail(user.getEmail(), subject, content);
            }
        });
    }



    /**
     * 发送文本邮件
     * @param to 定义发送给谁
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    @Async
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        logger.info("发送文本邮件开始：{},{},{}", to, subject, content);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("简单文本已经发送。");
        } catch (Exception e) {
            logger.error("发送文本邮件时发生异常！", e);
        }

    }

    /**
     * 发送html邮件
     * @param to 定义发送给谁
     * @param subject 邮件标题
     * @param content 邮件内容模板
     */
    @Async
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        //logger.info("发送html邮件开始：{},{},{}", to, subject, content);
        logger.info("发送html邮件开始：{},{}", to, subject);
        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }


    /**
     * 发送带附件的邮件
     * @param to 定义发送给谁
     * @param subject 邮件标题
     * @param content 邮件内容
     * @param filePath 附件
     */
    @Async
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath){
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); //true代表邮件内容支持多组件，如附件，图片等

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file); //添加附件，可多次调用该方法添加多个附件
            //helper.addAttachment("test"+fileName, file);

            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }


    /**
     * 发送正文中有静态资源（图片）的邮件
     * @param to 定义发送给谁
     * @param subject 邮件标题
     * @param content 邮件内容
     * @param imgPath 图片资源地址，也可以是其它静态资源
     * @param rscId 图片ID，用于在<img>标签中使用，从而显示图片
     */
    @Async
    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String imgPath, String rscId){
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(imgPath));
            helper.addInline(rscId, res); //重复调用addInline可添加多个图片

            mailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }
}
