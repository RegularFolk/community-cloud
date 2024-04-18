package com.ruoyi.blog.service.impl;

import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.Course;
import com.ruoyi.blog.domain.CourseChapter;
import com.ruoyi.blog.domain.dto.CourseListDto;
import com.ruoyi.blog.domain.dto.PostChapterDto;
import com.ruoyi.blog.domain.dto.PostCourseDto;
import com.ruoyi.blog.domain.dto.PostVideoDto;
import com.ruoyi.blog.domain.vo.CourseDetailVo;
import com.ruoyi.blog.domain.vo.CourseListVo;
import com.ruoyi.blog.domain.vo.VideoDetailVo;
import com.ruoyi.blog.enums.BlogOrderEnum;
import com.ruoyi.blog.enums.BlogStatusEnum;
import com.ruoyi.blog.enums.CourseListTypeEnum;
import com.ruoyi.blog.mapper.BlogMapper;
import com.ruoyi.blog.mapper.CourseMapper;
import com.ruoyi.blog.service.CourseService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.CntDto;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.domain.UserBasicInfoVo;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.sql.SqlUtil;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.mq.enums.BlogTypeEnum;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.RemoteUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private RemoteFileService remoteFileService;

    @Resource
    private RemoteUserService remoteUserService;

    @Override
    public int postCourse(PostCourseDto dto) {
        /*
        * 针对course表修改或删除
        * 对于chapter和video，先删除再重新插入
        * 存在bug，会导致原先video的浏览量和点赞量等清理
        * who cares？
        * */
        return 0;
    }

    @Override
    public int postChapter(PostChapterDto dto) {
        CourseChapter chapter = new CourseChapter();
        chapter.setCourseId(dto.getCourseId());
        chapter.setChapterOrder(dto.getChapterOrder());
        chapter.setTitle(dto.getTitle());
        chapter.setDesc(dto.getDesc());

        int flag;
        if (dto.getChapterId() != null && dto.getChapterId() > 0) {
            chapter.setChapterId(dto.getChapterId());
            flag = courseMapper.updateChapter(chapter);
        } else {
            flag = courseMapper.insertChapter(chapter);
        }

        return flag;
    }

    @Override
    public int postVideo(PostVideoDto dto) {
        Long userId = SecurityUtils.getUserId();
        Blog blog = new Blog();
        blog.setAuthorId(userId);
        blog.setTitle(dto.getTitle());
        blog.setPreview(dto.getPreview());
        blog.setStatus(BlogStatusEnum.PUBLISHED.getStatus());
        blog.setReleaseTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
        blog.setType(BlogTypeEnum.VIDEO.getType());
        blog.setCourseId(dto.getCourseId());
        blog.setVideoId(dto.getVideoId());
        blog.setChapterId(dto.getChapterId());
        blog.setChapterOrder(dto.getChapterOrder());

        int flag;
        if (dto.getId() != null && dto.getId() > 0) {
            blog.setId(dto.getId());
            flag = courseMapper.updateVideo(blog);
        } else {
            flag = blogMapper.insertBlog(blog);
        }

        return flag;
    }

    /**
     * 调用文件服务删除阿里云视频
     */
    @Override
    public int delVideo(IdDto dto) {
        return delVideoByIds(Collections.singletonList(dto.getId()));
    }

    /**
     * 删除课程需要连带删除所有章节和所有视频
     *
     * @param dto
     * @return
     */
    @Override
    public int delCourse(IdDto dto) {
        int flag = 0;
        Long userId = SecurityUtils.getUserId();
        Course course = courseMapper.selectCourseById(dto.getId(), userId);

        if (course == null) {
            return flag;
        }

        List<CourseChapter> chapterList = courseMapper.selectChapterByCourseId(course.getCourseId());

        if (CollectionUtils.isEmpty(chapterList)) {
            return flag;
        }

        List<Long> chapterIds = chapterList.stream().map(CourseChapter::getChapterId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(chapterIds)) {
            delChapterByIds(chapterIds);
        }

        return courseMapper.delCourse(course.getCourseId(), userId);
    }

    /**
     * 删除章节，连带删除所有视频
     *
     * @param dto
     * @return
     */
    @Override
    public int delChapter(IdDto dto) {
        return delChapterByIds(Collections.singletonList(dto.getId()));
    }

    @Override
    public List<CourseListVo> list(CourseListDto dto) {
        Course course = getListCourseDto(dto);
        Integer order = getListOrder(dto);
        List<Course> courseList = courseMapper.list(
                course,
                dto.getPageSize(),
                SqlUtil.getOffset(dto.getPageNum(), dto.getPageSize()),
                order);

        if (CollectionUtils.isEmpty(courseList)) {
            return new ArrayList<>();
        }

        return convertCourse2Vo(courseList);
    }

    private Integer getListOrder(CourseListDto dto) {
        CourseListTypeEnum listType = CourseListTypeEnum.getEnum(dto.getListType());

        switch (listType) {
            case RANDOM:
                return BlogOrderEnum.RAND.getOrder();
            case HOTTEST:
                return BlogOrderEnum.HOT_RANK.getOrder();
            case LATEST:
                return BlogOrderEnum.TIME_DESC.getOrder();
            default:
                break;
        }
        return null;
    }

    private Course getListCourseDto(CourseListDto dto) {
        CourseListTypeEnum listType = CourseListTypeEnum.getEnum(dto.getListType());
        Course course = new Course();
        switch (listType) {
            case AUTHOR:
                course.setAuthorId(dto.getUserId());
                break;
            case TITLE_SEARCH:
                course.setTitle(dto.getTitle());
                break;
            case RANDOM:
            case HOTTEST:
            case LATEST:
                break;
            default:
                // unreachable
                throw new RuntimeException("枚举值不存在");
        }
        return course;
    }

    @Override
    public long listTotal(CourseListDto dto) {
        Course course = getListCourseDto(dto);
        return courseMapper.listTotal(course);
    }

    @Override
    public CourseDetailVo courseDetail(IdDto dto) {
        CourseDetailVo vo = new CourseDetailVo();
        Course course = new Course();
        course.setCourseId(dto.getId());
        List<Course> list = courseMapper.list(course, null, null, null);
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("课程不存在！");
        }

        CourseListVo courseVo = convertCourse2Vo(list).get(0);
        vo.setCourse(courseVo);

        List<CourseChapter> chapterList = courseMapper.selectChapterByCourseId(dto.getId());
        if (CollectionUtils.isEmpty(chapterList)) {
            return vo;
        }

        Blog blog = new Blog();
        blog.setCourseId(dto.getId());
        List<Blog> videoList = blogMapper.getArticleList(blog, null, null, null);
        Map<Long, List<Blog>> videoMap = videoList.stream().collect(Collectors.groupingBy(Blog::getChapterId));

        List<CourseDetailVo.ChapterVo> chapterVoList = chapterList.stream().map(c -> {
            CourseDetailVo.ChapterVo chapterVo = new CourseDetailVo.ChapterVo();

            chapterVo.setChapterId(c.getChapterId());
            chapterVo.setChapterOrder(c.getChapterOrder());
            chapterVo.setTitle(c.getTitle());
            chapterVo.setDesc(c.getDesc());

            List<Blog> chapterVideoList = videoMap.get(c.getChapterId());
            if (!CollectionUtils.isEmpty(chapterVideoList)) {
                List<CourseDetailVo.VideoVo> videoVoList = chapterVideoList.stream().map(v -> {
                    CourseDetailVo.VideoVo videoVo = new CourseDetailVo.VideoVo();
                    videoVo.setId(v.getId());
                    videoVo.setTitle(v.getTitle());
                    videoVo.setPreview(v.getPreview());
                    videoVo.setCreateTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, v.getCreateTime()));
                    videoVo.setLikeCnt(v.getLikeCnt());
                    videoVo.setCommentCnt(v.getCommentCnt());
                    videoVo.setViewCnt(v.getViewCnt());
                    videoVo.setCollectCnt(v.getCollectCnt());
                    videoVo.setVideoId(v.getVideoId());
                    videoVo.setChapterOrder(v.getChapterOrder().toString());

                    return videoVo;
                }).sorted(Comparator.comparing(CourseDetailVo.VideoVo::getChapterOrder)).collect(Collectors.toList());

                chapterVo.setVideoList(videoVoList);
            }

            return chapterVo;
        }).sorted(Comparator.comparing(CourseDetailVo.ChapterVo::getChapterOrder)).collect(Collectors.toList());

        vo.setChapterList(chapterVoList);

        return vo;
    }

    @Override
    public VideoDetailVo videoDetail(IdDto dto) {
        Blog blog = new Blog();
        blog.setId(dto.getId());
        List<Blog> videos = blogMapper.getArticleList(blog, null, null, null);
        if (CollectionUtils.isEmpty(videos)) {
            throw new RuntimeException("视频不存在！");
        }
        Blog video = videos.get(0);
        String url = (String) remoteFileService.getVodUrl(video.getVideoId()).get("data");

        VideoDetailVo vo = new VideoDetailVo();
        vo.setId(video.getId());
        vo.setTitle(video.getTitle());
        vo.setPreview(video.getPreview());
        vo.setLikeCnt(video.getLikeCnt());
        vo.setCommentCnt(video.getCommentCnt());
        vo.setViewCnt(video.getViewCnt());
        vo.setCollectCnt(video.getCollectCnt());
        vo.setCourseId(video.getCourseId());
        vo.setVideoId(video.getVideoId());
        vo.setVideoUrl(url);
        vo.setChapterId(video.getChapterId());
        vo.setChapterOrder(video.getChapterOrder());

        return vo;
    }

    /**
     * 根据传入的 course 列表，分别查询：
     * 点赞、浏览、评论、收藏总数；
     * 作者基本信息
     *
     * @param courseList
     * @return
     */
    private List<CourseListVo> convertCourse2Vo(List<Course> courseList) {
        if (CollectionUtils.isEmpty(courseList)) {
            return new ArrayList<>();
        }

        List<Long> courseIdList = courseList.stream().map(Course::getCourseId).collect(Collectors.toList());

        List<Long> userIdList = courseList.stream().map(Course::getAuthorId).collect(Collectors.toList());

        List<CntDto> likeCntList = courseMapper.getLikeCntList(courseIdList);
        Map<Long, List<CntDto>> likeCntMap = CntDto.convert2Map(likeCntList);

        List<CntDto> viewCntList = courseMapper.getViewCntList(courseIdList);
        Map<Long, List<CntDto>> viewCntMap = CntDto.convert2Map(viewCntList);

        List<CntDto> commentCntList = courseMapper.getCommentCntList(courseIdList);
        Map<Long, List<CntDto>> commentCntMap = CntDto.convert2Map(commentCntList);

        List<CntDto> collectCntList = courseMapper.getCollectCntList(courseIdList);
        Map<Long, List<CntDto>> collectCntMap = CntDto.convert2Map(collectCntList);

        List<UserBasicInfoVo> userBasicInfoList = remoteUserService
                .getUserBasicInfoByIds(userIdList, SecurityConstants.INNER).getData();
        Map<Long, List<UserBasicInfoVo>> userIdMap =
                userBasicInfoList.stream().collect(Collectors.groupingBy(UserBasicInfoVo::getId));

        return courseList.stream().map(c -> {
            Long likeCnt = likeCntMap.get(c.getCourseId()).get(0).getCnt();
            Long viewCnt = viewCntMap.get(c.getCourseId()).get(0).getCnt();
            Long commentCnt = commentCntMap.get(c.getCourseId()).get(0).getCnt();
            Long collectCnt = collectCntMap.get(c.getCourseId()).get(0).getCnt();
            UserBasicInfoVo userInfo = userIdMap.get(c.getAuthorId()).get(0);

            CourseListVo vo = new CourseListVo();
            vo.setCourseId(c.getCourseId());
            vo.setTitle(c.getTitle());
            vo.setCoverPic(c.getCoverPic());
            vo.setDesc(c.getDesc());
            vo.setCreateTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, c.getCreateTime()));
            vo.setLikeCnt(likeCnt);
            vo.setViewCnt(viewCnt);
            vo.setCommentCnt(commentCnt);
            vo.setCollectCnt(collectCnt);
            vo.setUserBasicInfo(userInfo);

            return vo;
        }).collect(Collectors.toList());
    }

    private int delChapterByIds(List<Long> chapterIds) {
        Long userId = SecurityUtils.getUserId();
        int flag = 0;
        List<CourseChapter> chapterList = courseMapper.selectChapterByIds(chapterIds);

        if (CollectionUtils.isEmpty(chapterList)) {
            return flag;
        }

        List<Blog> blogList = blogMapper.getVideoByChapterIds(chapterIds, userId);

        if (!CollectionUtils.isEmpty(blogList)) {
            // 先把videoId取出来删掉视频，再删掉表记录
            List<Long> blogIds = blogList.stream().map(Blog::getId).collect(Collectors.toList());
            flag = delVideoByIds(blogIds);
            if (flag == 0) {
                return flag;
            }

            flag = courseMapper.delChapterByIds(chapterIds, userId);

        }

        return flag;
    }

    private int delVideoByIds(List<Long> blogIds) {
        List<Blog> blogs = blogMapper.getBlogByIds(blogIds);
        if (CollectionUtils.isEmpty(blogs)) {
            return 0;
        }

        String videoIds = blogs.stream().map(Blog::getVideoId).collect(Collectors.joining("[,]"));

        AjaxResult ajaxResult = remoteFileService.delVod(videoIds);

        if (ajaxResult.isError()) {
            return 0;
        }

        return blogMapper.deleteBlogByIds(blogIds, SecurityUtils.getUserId());
    }


}
