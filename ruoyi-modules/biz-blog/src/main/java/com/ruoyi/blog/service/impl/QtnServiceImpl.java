package com.ruoyi.blog.service.impl;

import com.ruoyi.blog.config.BlogConstants;
import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.BlogContent;
import com.ruoyi.blog.domain.dto.PostQtnDto;
import com.ruoyi.blog.domain.dto.QtnSquareDto;
import com.ruoyi.blog.domain.vo.ArticleVo;
import com.ruoyi.blog.domain.vo.QtnSquareVo;
import com.ruoyi.blog.enums.BlogOrderEnum;
import com.ruoyi.blog.enums.BlogStatusEnum;
import com.ruoyi.blog.mapper.BlogCollectedMapper;
import com.ruoyi.blog.mapper.BlogLikedMapper;
import com.ruoyi.blog.mapper.BlogMapper;
import com.ruoyi.blog.service.ArticleService;
import com.ruoyi.blog.service.QtnService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.UserBasicInfoVo;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.sql.SqlUtil;
import com.ruoyi.common.mq.enums.BlogTypeEnum;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteUserService;
import org.springframework.stereotype.Service;

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
    private BlogLikedMapper blogLikedMapper;

    @Resource
    private BlogCollectedMapper blogCollectedMapper;

    @Override
    public Long postQtn(PostQtnDto dto) {
        Long userId = SecurityUtils.getUserId();
        Blog blog = new Blog();
        blog.setAuthorId(userId);
        blog.setTitle(dto.getTitle());
        blog.setType(BlogTypeEnum.ARTICLE.getType());

        // 获取内容的前三百字为预览
        String content = dto.getContent();
        if (content.length() <= BlogConstants.PREVIEW_LENGTH) {
            blog.setPreview(content);
        } else {
            blog.setPreview(content.substring(0, 300));
        }

        Long articleId = dto.getArticleId();
        boolean updateFlag = articleId != null && articleId != 0;
        if (updateFlag) {
            blog.setId(articleId);
            blogMapper.updateBlogById(blog);
        } else {
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

        ansCnt.forEach(e -> ansCntMap.put(Long.valueOf(e.get("q_id")), Long.valueOf(e.get("ans_cnt"))));

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
            vo.setPreview(q.getPreview());
            vo.setAnsCnt(ansCntMap.get(q.getId()));
            vo.setLikeCnt(q.getLikeCnt());
            vo.setViewCnt(q.getViewCnt());
            vo.setCollectCnt(q.getCollectCnt());
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
        return articleService.getArticle(dto.getId());
    }
}
















