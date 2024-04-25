package com.ruoyi.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.blog.config.BlogConstants;
import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.BlogContent;
import com.ruoyi.blog.domain.dto.PostQtnDto;
import com.ruoyi.blog.domain.dto.QtnAnsAcptDto;
import com.ruoyi.blog.domain.dto.QtnAnsListDto;
import com.ruoyi.blog.domain.dto.QtnSquareDto;
import com.ruoyi.blog.domain.vo.ArticleVo;
import com.ruoyi.blog.domain.vo.QtnAnsVo;
import com.ruoyi.blog.domain.vo.QtnSquareVo;
import com.ruoyi.blog.enums.ArticleClassificationEnum;
import com.ruoyi.blog.enums.BlogOrderEnum;
import com.ruoyi.blog.enums.BlogStatusEnum;
import com.ruoyi.blog.mapper.BlogCollectedMapper;
import com.ruoyi.blog.mapper.BlogLikedMapper;
import com.ruoyi.blog.mapper.BlogMapper;
import com.ruoyi.blog.service.ArticleService;
import com.ruoyi.blog.service.MailService;
import com.ruoyi.blog.service.QtnService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.ListDto;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.UserBasicInfoVo;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.sql.SqlUtil;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.mq.enums.BlogTypeEnum;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteNotifyService;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QtnServiceImpl implements QtnService {

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private ArticleService articleService;

    @Resource
    private RemoteUserService remoteUserService;

    @Resource
    private MailService mailService;

    @Resource
    private BlogLikedMapper blogLikedMapper;

    @Resource
    private BlogCollectedMapper blogCollectedMapper;

    @Override
    public Long postQtn(PostQtnDto dto) {
        Long userId = SecurityUtils.getUserId();
        Blog blog = new Blog();
        blog.setAuthorId(userId);
        blog.setTitle(dto.getTitle());
        blog.setType(BlogTypeEnum.QUESTION.getType());

        // 获取内容的前三百字为预览
        String content = dto.getContent();
        if (content.length() <= BlogConstants.PREVIEW_LENGTH) {
            blog.setPreview(content);
        } else {
            blog.setPreview(content.substring(0, 300));
        }

        Long articleId = dto.getArticleId();
        Long qtnPId = dto.getQtnPId();
        boolean updateFlag = articleId != null && articleId != 0;
        boolean ansFlag = qtnPId != null && qtnPId != 0;
        if (updateFlag) {
            blog.setId(articleId);
            blogMapper.updateBlogById(blog);
        } else {
            String receiverName = remoteUserService.getUserBasicInfoByIds(Collections.singletonList(userId), SecurityConstants.INNER).getData().get(0).getNickName();
            if (ansFlag) {
                blog.setQtnPId(qtnPId);
                blog.setType(BlogTypeEnum.ANSWER.getType());
                // 不可以重复回答问题
                Blog tempBlog = new Blog();
                tempBlog.setQtnPId(qtnPId);
                tempBlog.setAuthorId(userId);
                long existCnt = blogMapper.getArticleCnt(tempBlog);
                // 测试代码，允许重复回复
                if (existCnt > 0) {
                    return 0L;
                }

                Blog qtn = blogMapper.getBlogByIds(Collections.singletonList(qtnPId)).get(0);

                // 进行系统通知，通知问题的发布者
                mailService.systemNotify(
                        Collections.singletonList(qtn.getAuthorId()),
                        "有人回答了您的提问！",
                        "您好！用户 " + receiverName + " 刚刚回答了您的问题：" + qtn.getTitle() + " \n赶快上线看看吧！");

            }
            if (!ansFlag) {
                blog.setArticleClassify((long) ArticleClassificationEnum.QUESTION.getClassification());
                // 进行系统通知，新增问题，通知关注者
                mailService.systemNotifyToFollowers(
                        "您的关注者发布了一个新的问题！",
                        "您关注的用户：" + receiverName + " 刚刚发布了新的问题：" + blog.getTitle()
                );

            }
            blog.setReleaseTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
            blog.setStatus(BlogStatusEnum.PUBLISHED.getStatus());
            blogMapper.insertBlog(blog);
        }

        BlogContent blogContent = new BlogContent();
        blogContent.setBlogId(blog.getId());
        blogContent.setContent(dto.getContent());
        blogContent.setContentFormatting(dto.getContentFormatting());
        blogContent.setContentHtml(dto.getContentHtml());

        if (updateFlag) {
            blogMapper.updateBlogContent(blogContent);
        } else {
            blogMapper.insertBlogContent(blogContent);
        }

        return blog.getId();
    }

    @Override
    public int delQtn(IdDto dto) {
        return articleService.deleteArticle(dto);
    }

    @Override
    public List<QtnSquareVo> list(QtnSquareDto dto) {
        Blog blog = new Blog();
        blog.setType(BlogTypeEnum.QUESTION.getType());
        blog.setStatus(BlogStatusEnum.PUBLISHED.getStatus());

        BlogOrderEnum orderEnum = BlogOrderEnum.getEnum(dto.getBlogOrder());

        List<Blog> qtnList = blogMapper.getArticleList(
                blog,
                dto.getPageSize(),
                SqlUtil.getOffset(dto.getPageNum(), dto.getPageSize()),
                orderEnum.getOrder()
        );

        if (CollectionUtils.isEmpty(qtnList)) {
            return new ArrayList<>();
        }

        // 查询作者个人信息
        List<Long> authorIdList = qtnList.stream().map(Blog::getAuthorId).collect(Collectors.toList());
        List<UserBasicInfoVo> basicInfoList = remoteUserService
                .getUserBasicInfoByIds(authorIdList, SecurityConstants.INNER).getData();
        Map<Long, List<UserBasicInfoVo>> basicInfoMap = basicInfoList
                .stream().collect(Collectors.groupingBy(UserBasicInfoVo::getId));

        // 查询是否点赞、收藏信息、回答数
        String userId = SecurityUtils.getUsername();
        List<Long> qtnIdList = qtnList.stream().map(Blog::getId).collect(Collectors.toList());

        // 这里的 map 是key为列名，value为列值，需要转换为key为qId，value为ansCnt的格式
        List<Map<String, String>> ansCnt = blogMapper.getAnsCntByIds(qtnIdList);

        Map<Long, Long> ansCntMap = new HashMap<>();

        ansCnt.forEach(e -> ansCntMap.put(SqlUtil.cast2Long(e.get("q_id")), SqlUtil.cast2Long(e.get("ans_cnt"))));

        List<Long> likedIds = blogLikedMapper.selectLikedIds(qtnIdList, userId);
        Set<Long> likedSet = new HashSet<>(likedIds);

        List<Long> collectedIds = blogCollectedMapper.selectCollectedIds(qtnIdList, userId);
        Set<Long> collectSet = new HashSet<>(collectedIds);

        return qtnList.stream().map(q -> {
            UserBasicInfoVo basicInfoVo = basicInfoMap.get(q.getAuthorId()).get(0);
            QtnSquareVo vo = new QtnSquareVo();
            vo.setQtnId(q.getId());
            vo.setAuthor(basicInfoVo);
            vo.setTitle(q.getTitle());
            vo.setPreview(q.getPreview());
            Long ac = ansCntMap.get(q.getId());
            vo.setAnsCnt(ac == null ? 0 : ac);
            vo.setLikeCnt(q.getLikeCnt());
            vo.setViewCnt(q.getViewCnt());
            vo.setCollectCnt(q.getCollectCnt());
            vo.setPublishTime(q.getReleaseTime());
            vo.setLiked(likedSet.contains(q.getId()));
            vo.setCollected(collectSet.contains(q.getId()));
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public int collect(IdDto dto, BlogTypeEnum typeEnum) {
        return articleService.collect(dto, typeEnum);
    }

    @Override
    public ArticleVo detail(IdDto dto) {
        return articleService.getArticle(dto.getId(), BlogTypeEnum.QUESTION);
    }

    @Override
    public QtnAnsVo ansList(QtnAnsListDto dto) {
        // todo 置顶已采纳回答（需要修改的代码比较多）
        QtnAnsVo vo = new QtnAnsVo();
        Blog blog = new Blog();
        blog.setType(BlogTypeEnum.ANSWER.getType());
        blog.setQtnPId(dto.getQtnId());
        List<Blog> ansList = blogMapper.getArticleList(
                blog,
                dto.getPageSize(),
                SqlUtil.getOffset(dto.getPageNum(), dto.getPageSize()),
                BlogOrderEnum.HOT_RANK.getOrder());

        if (CollectionUtils.isEmpty(ansList)) {
            vo.setAnsList(new ArrayList<>());
            vo.setTotal(0);
            return vo;
        }

        List<Long> ansIdList = ansList.stream().map(Blog::getId).collect(Collectors.toList());
        List<Long> authorIdList = ansList.stream().map(Blog::getAuthorId).collect(Collectors.toList());

        List<BlogContent> contentList = blogMapper.getContentByIds(ansIdList);
        List<SysUser> userList = remoteUserService.getInfoByIds(authorIdList, SecurityConstants.INNER).getData();


        Map<Long, List<BlogContent>> contentMap = contentList.stream()
                .collect(Collectors.groupingBy(BlogContent::getBlogId));

        Map<Long, List<SysUser>> userMap = userList.stream().collect(Collectors.groupingBy(SysUser::getUserId));

        String userId = SecurityUtils.getUserId().toString();
        List<Long> likedIds = blogLikedMapper.selectLikedIds(ansIdList, userId);
        Set<Long> likedSet = new HashSet<>(likedIds);

        List<Long> collectIds = blogCollectedMapper.selectCollectedIds(ansIdList, userId);
        Set<Long> collectSet = new HashSet<>(collectIds);

        // 查询用户基本信息
        R<List<UserBasicInfoVo>> resp = remoteUserService.getUserBasicInfoByIds(authorIdList, SecurityConstants.INNER);
        List<UserBasicInfoVo> userInfoList = resp.getData();
        Map<Long, List<UserBasicInfoVo>> userInfoMap = userInfoList
                .stream().collect(Collectors.groupingBy(UserBasicInfoVo::getId));

        long total = blogMapper.getArticleCnt(blog);

        List<ArticleVo> articleVoList = ansList.stream().map(a -> {
            ArticleVo articleVo = new ArticleVo();
            BlogContent blogContent = contentMap.get(a.getId()).get(0);
            SysUser sysUser = userMap.get(a.getAuthorId()).get(0);
            UserBasicInfoVo basicInfo = userInfoMap.get(a.getAuthorId()).get(0);
            articleVo.setArticleId(a.getId());
            articleVo.setAuthorId(a.getAuthorId());
            articleVo.setTitle(a.getTitle());
            articleVo.setSenderName(sysUser.getNickName());
            articleVo.setPublishTime(a.getReleaseTime());
            articleVo.setContentFormatting(blogContent.getContentFormatting());
            articleVo.setContentHtml(blogContent.getContentHtml());
            articleVo.setViewCnt(a.getViewCnt());
            articleVo.setLikeCnt(a.getLikeCnt());
            articleVo.setCommentCnt(a.getCommentCnt());
            articleVo.setCollectCnt(a.getCollectCnt());
            articleVo.setLiked(likedSet.contains(a.getId()));
            articleVo.setStatus(a.getStatus());
            articleVo.setUserBasicInfo(basicInfo);
            articleVo.setCollected(collectSet.contains(a.getId()));
            articleVo.setAccepted(a.getStatus().equals(BlogStatusEnum.ACCEPTED.getStatus()));
            return articleVo;
        }).collect(Collectors.toList());

        vo.setAnsList(articleVoList);
        vo.setTotal((int) total);
        return vo;
    }

    @Override
    public int accept(QtnAnsAcptDto dto) {
        // 判断是否已存在采纳回答
        Long userId = SecurityUtils.getUserId();
        int cnt = blogMapper.getAcptAnsCnt(dto.getQtnId());
        if (cnt > 0) {
            return 0;
        }

        return blogMapper.acceptAns(dto.getAnsId(), dto.getQtnId(), userId);
    }

    @Override
    public boolean hasAnswered(IdDto dto) {
        Blog blog = new Blog();
        blog.setAuthorId(SecurityUtils.getUserId());
        blog.setQtnPId(dto.getId());
        long ansCnt = blogMapper.getArticleCnt(blog);
        return ansCnt > 0;
    }

    @Override public boolean hasAccepted(IdDto dto) {
        Blog blog = new Blog();
        blog.setStatus(BlogStatusEnum.ACCEPTED.getStatus());
        blog.setQtnPId(dto.getId());
        long acceptCnt = blogMapper.getArticleCnt(blog);
        return acceptCnt > 0;
    }
}
















