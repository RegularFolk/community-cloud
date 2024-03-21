package com.ruoyi.blog.service.impl;

import com.ruoyi.blog.config.BlogConstants;
import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.BlogCollected;
import com.ruoyi.blog.domain.BlogContent;
import com.ruoyi.blog.domain.PersonalClassification;
import com.ruoyi.blog.domain.dto.*;
import com.ruoyi.blog.domain.vo.*;
import com.ruoyi.blog.enums.ArticleRankEnum;
import com.ruoyi.blog.enums.BlogOrderEnum;
import com.ruoyi.blog.enums.BlogStatusEnum;
import com.ruoyi.blog.enums.DeletePersonClassTypeEnum;
import com.ruoyi.blog.mapper.BlogCollectedMapper;
import com.ruoyi.blog.mapper.BlogLikedMapper;
import com.ruoyi.blog.mapper.BlogMapper;
import com.ruoyi.blog.service.ArticleService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.UserBasicInfoVo;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.sql.SqlUtil;
import com.ruoyi.common.mq.callBack.DefaultCallBack;
import com.ruoyi.common.mq.constants.MqTopicConstants;
import com.ruoyi.common.mq.domain.BlogMessage;
import com.ruoyi.common.mq.enums.BlogTypeEnum;
import com.ruoyi.common.mq.enums.OperateType;
import com.ruoyi.common.redis.constants.RankConfig;
import com.ruoyi.common.redis.constants.RedisPrefix;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private BlogCollectedMapper blogCollectedMapper;

    @Resource
    private BlogLikedMapper blogLikedMapper;

    @Resource
    private RemoteUserService remoteUserService;

    @Resource
    private RocketMQTemplate rocketmqTemplate;

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    @Override
    public Long postArticle(PostArticleDto dto) {
        Blog blog = new Blog();
        Long userId = SecurityUtils.getUserId();
        blog.setAuthorId(userId);
        blog.setTitle(dto.getTitle());
        blog.setStatus(dto.getStatus());
        blog.setArticleClassify(dto.getArticleClassify());
        blog.setPersonClassify(dto.getPersonClassify());
        blog.setType(BlogTypeEnum.ARTICLE.getType());

        if (blog.getStatus().equals(BlogStatusEnum.PUBLISHED.getStatus())) {
            blog.setReleaseTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
        }

        // 获取内容的前三百字为预览
        String content = dto.getContent();
        if (content.length() <= BlogConstants.PREVIEW_LENGTH) {
            blog.setPreview(content);
        } else {
            blog.setPreview(content.substring(0, 300));
        }

        // 如果dto存在id字段为更新，否则为删除
        boolean updateFlag = dto.getArticleId() != null && dto.getArticleId() != 0;
        if (updateFlag) {
            blog.setId(dto.getArticleId());
            blogMapper.updateBlogById(blog);
        } else {
            blogMapper.insertBlog(blog);
        }

        BlogContent blogContent = new BlogContent();
        blogContent.setBlogId(blog.getId());
        blogContent.setContent(dto.getContent());
        blogContent.setContentFormatting(dto.getContentFormatting());
        blogContent.setContentHtml(dto.getContentHtml());

        if (updateFlag){
            blogMapper.updateBlogContent(blogContent);
        } else {
            blogMapper.insertBlogContent(blogContent);
        }

        return blog.getId();
    }

    @Override
    public int postPersonClassification(PostArticleClassDto dto) {
        PersonalClassification ac = new PersonalClassification();
        Long userId = SecurityUtils.getUserId();
        ac.setUserId(userId);
        ac.setClassName(dto.getClassName());
        return blogMapper.insertPersonClassification(ac);
    }

    @Override
    public int deletePersonClass(DeletePersonClassDto dto) {
        blogMapper.deletePersonClassificationById(dto.getClassId());
        Long userId = SecurityUtils.getUserId();
        Blog blog = new Blog();
        blog.setPersonClassify(dto.getClassId());
        blog.setType(BlogTypeEnum.ARTICLE.getType());
        blog.setAuthorId(userId);
        List<Blog> articleList = blogMapper.getArticleList(blog, null, null, null);
        List<Long> idList = articleList.stream().map(Blog::getId).collect(Collectors.toList());
        int cnt = 0;
        if (DeletePersonClassTypeEnum.DELETE_ALL.getType() == dto.getDeleteType()) {
            // 删除所有分类下的文章
            cnt = blogMapper.deleteBlogByIds(idList);
            blogMapper.deleteContentByIds(idList);
        } else if (DeletePersonClassTypeEnum.MOVE_TO_DEFAULT.getType() == dto.getDeleteType()) {

            cnt = blogMapper.moveToDefaultClass(userId, idList);
        }

        return cnt;
    }

    @Override
    public int deleteArticle(IdDto id) {
        Long userId = SecurityUtils.getUserId();
        int blogDeleteCnt = blogMapper.deleteBlogById(id.getId(), userId);
        if (blogDeleteCnt == 0) {
            throw new ServiceException("删除失败！");
        }
        blogMapper.deleteContentById(id.getId());

        return blogDeleteCnt;
    }

    @Override
    public List<ArticleQueryVo> getArticleList(ArticleQueryDto dto) {
        List<ArticleQueryVo> voList = new ArrayList<>();
        Long userId = SecurityUtils.getUserId();
        // 先查出随笔对象，再根据随笔对象查询出个人分类，组装返回
        Blog blog = new Blog();
        blog.setAuthorId(userId);
        blog.setTitle(dto.getTitle());
        blog.setStatus(dto.getStatus());
        blog.setArticleClassify(dto.getArticleClassification());
        blog.setPersonClassify(dto.getPersonClassification());
        blog.setType(BlogTypeEnum.ARTICLE.getType());

        List<Blog> blogList =
                blogMapper.getArticleList(blog, dto.getPageSize(), (dto.getPageNum() - 1) * dto.getPageSize(), null);
        if (CollectionUtils.isEmpty(blogList)) {
            return voList;
        }
        List<Long> personClassIdList = blogList
                .stream().map(Blog::getPersonClassify).distinct().collect(Collectors.toList());
        List<PersonalClassification> pClassList = blogMapper.getPersonClassByIds(personClassIdList);
        Map<Long, List<Blog>> groupByPClass = blogList
                .stream().collect(Collectors.groupingBy(Blog::getPersonClassify));

        // 组装数据
        voList = pClassList.stream().map(pc -> {
            ArticleQueryVo articleVo = new ArticleQueryVo();
            articleVo.setClassId(pc.getId());
            articleVo.setClassName(pc.getClassName());
            articleVo.setCreateTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, pc.getCreateTime()));

            List<Blog> bList = groupByPClass.get(pc.getId());

            List<ArticleQueryVo.ArticleQueryChild> children = new ArrayList<>();
            if (!CollectionUtils.isEmpty(bList)) {
                children = bList.stream().map(b -> {
                    ArticleQueryVo.ArticleQueryChild child = new ArticleQueryVo.ArticleQueryChild();
                    child.setArticleId(b.getId());
                    child.setTitle(b.getTitle());
                    child.setPreview(b.getPreview());
                    child.setStatus(b.getStatus());
                    child.setArticleClassify(b.getArticleClassify().toString());
                    child.setPublishTime(b.getReleaseTime());
                    child.setCreateTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, b.getCreateTime()));
                    child.setLikeCnt(b.getLikeCnt());
                    child.setCommentCnt(b.getCommentCnt());
                    child.setViewCnt(b.getViewCnt());
                    child.setCollectCnt(b.getCollectCnt());
                    return child;
                }).sorted((a, b) -> b.getCreateTime().compareTo(a.getCreateTime())).collect(Collectors.toList());
            }
            articleVo.setChildren(children);
            return articleVo;
        }).collect(Collectors.toList());

        // 设置id
        int cursor = 1;
        for (ArticleQueryVo queryVo : voList) {
            queryVo.setId(cursor++);
            List<ArticleQueryVo.ArticleQueryChild> children = queryVo.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                for (ArticleQueryVo.ArticleQueryChild child : children) {
                    child.setId(cursor++);
                }
            }
        }

        return voList;
    }

    @Override
    public List<PersonClassVo> getPersonClassList(Long userId) {
        List<PersonalClassification> pcList = blogMapper.getPersonClassByUserId(userId);
        return pcList.stream().map(pc -> {
            PersonClassVo vo = new PersonClassVo();
            vo.setValue(pc.getId());
            vo.setLabel(pc.getClassName());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public long getTotalArticle(ArticleQueryDto dto) {
        Long userId = SecurityUtils.getUserId();
        // 先查出随笔对象，再根据随笔对象查询出个人分类，组装返回
        Blog blog = new Blog();
        blog.setAuthorId(userId);
        blog.setTitle(dto.getTitle());
        blog.setStatus(dto.getStatus());
        blog.setArticleClassify(dto.getArticleClassification());
        blog.setPersonClassify(dto.getPersonClassification());
        blog.setType(BlogTypeEnum.ARTICLE.getType());
        return blogMapper.getArticleCnt(blog);
    }

    @Override
    public ArticleVo getArticle(Long articleId) {
        ArticleVo vo = new ArticleVo();
        Blog blog = new Blog();
        Long userId = SecurityUtils.getUserId();
        blog.setType(BlogTypeEnum.ARTICLE.getType());
        blog.setId(articleId);
        List<Blog> articleList = blogMapper.getArticleList(blog, null, null, null);
        if (CollectionUtils.isEmpty(articleList)) {
            throw new ServiceException("随笔不存在!");
        }
        blog = articleList.get(0);
        BlogContent articleContent = blogMapper.getArticleContent(articleId);
        List<Long> userIdList = new ArrayList<>();
        userIdList.add(blog.getAuthorId());
        R<List<SysUser>> r = remoteUserService.getInfoByIds(userIdList, SecurityConstants.INNER);
        SysUser sysUser = r.getData().get(0);

        int likedFlag = blogLikedMapper.isLiked(userId, articleId);

        // 消息通知下游增加blog浏览计数
        BlogMessage message = new BlogMessage();
        message.setMessageId(articleId);
        message.setBlogId(articleId);
        message.setBlogType(blog.getType());
        message.setType(BlogMessage.MessageType.VIEW.getType());
        message.setOperateType(OperateType.ADD.getType());
        rocketmqTemplate.asyncSendOrderly(
                MqTopicConstants.BLOG_TOPIC,
                message,
                String.valueOf(message.getMessageId()),
                new DefaultCallBack<>(this.getClass(), message)
        );

        // 判断该随笔是否被收藏
        int collectFlag = blogCollectedMapper.isCollected(articleId, userId);

        vo.setArticleId(articleId);
        vo.setAuthorId(blog.getAuthorId());
        vo.setTitle(blog.getTitle());
        vo.setSenderName(sysUser.getNickName());
        if (StringUtils.isNotEmpty(blog.getReleaseTime())) {
            vo.setPublishTime(blog.getReleaseTime());
        }
        vo.setCommentCnt(blog.getCommentCnt());
        vo.setLikeCnt(blog.getLikeCnt());
        vo.setViewCnt(blog.getViewCnt());
        vo.setCollectCnt(blog.getCollectCnt());
        vo.setCollected(collectFlag > 0);
        vo.setStatus(blog.getStatus());
        vo.setContentFormatting(articleContent.getContentFormatting());
        vo.setContentHtml(articleContent.getContentHtml());
        vo.setPersonClassify(blog.getPersonClassify());
        vo.setArticleClassify(blog.getArticleClassify());
        vo.setLiked(likedFlag > 0);

        return vo;
    }

    @Override
    public int collect(IdDto dto) {
        Long articleId = dto.getId();
        Long userId = SecurityUtils.getUserId();
        BlogCollected blogCollected = new BlogCollected();
        blogCollected.setBlogId(articleId);
        blogCollected.setUserId(userId);

        int flag = blogCollectedMapper.insertBlogCollected(blogCollected);

        if (flag > 0) {
            // 通知下游改变收藏计数
            BlogMessage message = new BlogMessage();
            message.setMessageId(dto.getId());
            message.setBlogId(dto.getId());
            message.setBlogType(BlogTypeEnum.ARTICLE.getType());
            message.setOperateType(OperateType.ADD.getType());
            message.setType(BlogMessage.MessageType.COLLECT.getType());
            rocketmqTemplate.asyncSendOrderly(
                    MqTopicConstants.BLOG_TOPIC,
                    message,
                    String.valueOf(message.getMessageId()),
                    new DefaultCallBack<>(this.getClass(), message)
            );

        }

        return flag;
    }

    @Override
    public ArticleCollectQueryVo collectList(ArticleCollectQueryDto dto) {
        ArticleCollectQueryVo vo = new ArticleCollectQueryVo();
        Long userId = SecurityUtils.getUserId();
        List<ArticleCollectQueryVoUnit> voUnitList = blogCollectedMapper.collectList(userId, dto, dto.getPageSize(), (dto.getPageNum() - 1) * dto.getPageSize());
        long total = blogCollectedMapper.getCollectedTotal(userId);

        // 获取作者昵称
        if (!CollectionUtils.isEmpty(voUnitList)) {
            List<Long> userIdList = voUnitList
                    .stream().map(ArticleCollectQueryVoUnit::getAuthorId)
                    .distinct().collect(Collectors.toList());
            R<List<SysUser>> infoByIds = remoteUserService.getInfoByIds(userIdList, SecurityConstants.INNER);
            List<SysUser> userList = infoByIds.getData();
            Map<Long, List<SysUser>> groupById = userList.stream().collect(Collectors.groupingBy(SysUser::getUserId));
            voUnitList.forEach(unit -> unit.setAuthorName(groupById.get(unit.getAuthorId()).get(0).getNickName()));
        }

        vo.setList(voUnitList);
        vo.setTotal(total);
        return vo;
    }

    @Override
    public int cancelCollect(IdDto dto) {
        Long userId = SecurityUtils.getUserId();

        int flag = blogCollectedMapper.deleteCollect(dto.getId(), userId);

        if (flag > 0) {
            // 通知下游改变收藏计数
            BlogMessage message = new BlogMessage();
            message.setMessageId(dto.getId());
            message.setBlogId(dto.getId());
            message.setBlogType(BlogTypeEnum.ARTICLE.getType());
            message.setOperateType(OperateType.CANCEL.getType());
            message.setType(BlogMessage.MessageType.COLLECT.getType());
            rocketmqTemplate.asyncSendOrderly(
                    MqTopicConstants.BLOG_TOPIC,
                    message,
                    String.valueOf(message.getMessageId()),
                    new DefaultCallBack<>(this.getClass(), message)
            );
        }

        return flag;
    }

    @Override
    public PersonArticleVo getPersonalArticle(PersonArticleDto dto) {
        Blog blog = new Blog();
        blog.setType(BlogTypeEnum.ARTICLE.getType());
        blog.setAuthorId(dto.getUserId());
        blog.setStatus(BlogStatusEnum.PUBLISHED.getStatus());

        List<Blog> articleList = blogMapper.getArticleList(
                blog,
                dto.getPageSize(),
                SqlUtil.getOffset(dto.getPageNum(), dto.getPageSize()),
                BlogOrderEnum.PUBLISH_TIME_DESC.getOrder()
        );

        if (CollectionUtils.isEmpty(articleList)) {
            return new PersonArticleVo();
        }
        PersonArticleVo vo = new PersonArticleVo();

        List<PersonArticleVo.Unit> unitList = articleList.stream().map(a -> {
            PersonArticleVo.Unit unit = new PersonArticleVo.Unit();
            unit.setArticleId(a.getId());
            unit.setTitle(a.getTitle());
            unit.setPublishTime(a.getReleaseTime());
            unit.setCommentCnt(a.getCommentCnt());
            unit.setLikeCnt(a.getLikeCnt());
            unit.setViewCnt(a.getViewCnt());
            unit.setCollectCnt(a.getCollectCnt());

            return unit;
        }).collect(Collectors.toList());

        long total = blogMapper.getArticleCnt(blog);

        vo.setTotal(total);
        vo.setList(unitList);
        return vo;
    }

    @Override
    public int addPersonClass(Long userId, String className) {
        PersonalClassification pc = new PersonalClassification();
        pc.setClassName(className);
        pc.setUserId(userId);

        return blogMapper.insertPersonClassification(pc);
    }

    @Override
    public List<ArticleSquareVo> getArticleSquareList(ArticleQueryDto dto) {
        Long pc = dto.getArticleClassification();
        Blog blog = new Blog();
        blog.setStatus(BlogStatusEnum.PUBLISHED.getStatus());
        blog.setType(BlogTypeEnum.ARTICLE.getType());
        if (pc != null && pc != 0L) {
            blog.setArticleClassify(pc);
        }

        List<Blog> articleList = blogMapper.getArticleList(
                blog,
                dto.getPageSize(),
                0,
                BlogOrderEnum.RAND.getOrder()
        );

        // 查询作者个人信息
        List<Long> authorIdList = articleList.stream().map(Blog::getAuthorId).collect(Collectors.toList());
        List<UserBasicInfoVo> basicInfoList = remoteUserService
                .getUserBasicInfoByIds(authorIdList, SecurityConstants.INNER).getData();
        Map<Long, List<UserBasicInfoVo>> basicInfoMap = basicInfoList
                .stream().collect(Collectors.groupingBy(UserBasicInfoVo::getId));

        // 查询是否点赞、收藏信息
        String userId = SecurityUtils.getUsername();
        List<Long> articleIdList = articleList.stream().map(Blog::getId).collect(Collectors.toList());

        List<Long> likedIds = blogLikedMapper.selectLikedIds(articleIdList, userId);
        Set<Long> likedSet = new HashSet<>(likedIds);

        List<Long> collectedIds = blogCollectedMapper.selectCollectedIds(articleIdList, userId);
        Set<Long> collectSet = new HashSet<>(collectedIds);

        // 组装数据
        return articleList.stream().map(article -> {
            ArticleSquareVo vo = new ArticleSquareVo();
            UserBasicInfoVo basicInfo = basicInfoMap.get(article.getAuthorId()).get(0);
            vo.setArticleId(article.getId());
            vo.setArticleClassification(article.getArticleClassify().intValue());
            vo.setAuthor(basicInfo);
            vo.setTitle(article.getTitle());
            vo.setPreview(article.getPreview());
            vo.setPublishTime(article.getReleaseTime());
            vo.setCommentCnt(article.getCommentCnt());
            vo.setLikeCnt(article.getLikeCnt());
            vo.setViewCnt(article.getViewCnt());
            vo.setCollectCnt(article.getCollectCnt());
            vo.setLiked(likedSet.contains(article.getId()));
            vo.setCollected(collectSet.contains(article.getId()));

            return vo;
        }).collect(Collectors.toList());
    }

    //TODO 此处存在bug，没有排除掉作者设置隐藏的博客（一个解决方案：查多轮，直到凑出10条未隐藏博客）
    @Override
    public List<BlogRankVo> getRank(ArticleRankEnum type) {
        String rankPrefix = "";
        String recPrefix = "";

        switch (type) {
            case VIEW:
                rankPrefix = RedisPrefix.ARTICLE_VIEW_RANK;
                recPrefix = RedisPrefix.ARTICLE_VIEW;
                break;
            case LIKE:
                rankPrefix = RedisPrefix.ARTICLE_LIKE_RANK;
                recPrefix = RedisPrefix.ARTICLE_LIKE;
                break;
            case COLLECT:
                rankPrefix = RedisPrefix.ARTICLE_COLLECT_RANK;
                recPrefix = RedisPrefix.ARTICLE_COLLECT;
                break;
            default:
                // unreachable
                break;
        }

        String redisKey = rankPrefix + BlogTypeEnum.ARTICLE.getType() + ":" + DateUtils.dateTime();
        List<Long> idList;
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
            // 如果不存在该key，制作排行榜
            buildRank(rankPrefix, recPrefix);
        }
        Set<ZSetOperations.TypedTuple<Long>> typedTuples = redisTemplate
                .opsForZSet().rangeWithScores(redisKey, RankConfig.ARTICLE_RANK_START, RankConfig.ARTICLE_RANK_END);

        if (CollectionUtils.isEmpty(typedTuples)) {
            return new ArrayList<>();
        }

        idList = typedTuples.stream().filter(t-> !Long.valueOf(0L).equals(t.getValue())).sorted((a, b) -> {
            Double aScore = a.getScore();
            Double bScore = b.getScore();
            if (aScore == null && bScore == null) {
                return 0;
            } else if (aScore== null || bScore == null) {
                return aScore == null ? 1 : -1;
            } else {
                return bScore.compareTo(aScore);
            }
        }).map(ZSetOperations.TypedTuple::getValue).collect(Collectors.toList());

        Map<Long, List<Blog>> blogMap = blogMapper.getBlogByIds(idList)
                .stream().collect(Collectors.groupingBy(Blog::getId));

        return idList.stream().map(id -> {
            BlogRankVo vo = new BlogRankVo();
            vo.setBlogId(id);
            vo.setTitle(blogMap.get(id).get(0).getTitle());
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 制作前七天的排行榜
     *
     * @param rankPrefix
     * @param recPrefix
     */
    private void buildRank(String rankPrefix, String recPrefix) {
        String destKey = rankPrefix + BlogTypeEnum.ARTICLE.getType() + ":" + DateUtils.dateTime();
        String key = "";
        List<String> recKeyList = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            StringBuilder sb = new StringBuilder();
            String dateStr = DateUtils.parseDateToStr("yyyyMMdd", DateUtils.addDays(new Date(), -1 * i));
            sb.append(recPrefix)
                    .append(BlogTypeEnum.ARTICLE.getType())
                    .append(":").append(dateStr);
            if (i == 0) {
                key = sb.toString();
            } else {
                recKeyList.add(sb.toString());
            }
        }
        // 开头的key不能为空
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForZSet().add(key, 0L, 0);
        }

        redisTemplate.opsForZSet().unionAndStore(key, recKeyList, destKey);
        redisTemplate.expire(destKey, 1, TimeUnit.DAYS);
    }


}
