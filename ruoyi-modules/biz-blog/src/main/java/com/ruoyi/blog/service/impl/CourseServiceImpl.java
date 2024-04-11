package com.ruoyi.blog.service.impl;

import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.Course;
import com.ruoyi.blog.domain.CourseChapter;
import com.ruoyi.blog.domain.dto.PostChapterDto;
import com.ruoyi.blog.domain.dto.PostCourseDto;
import com.ruoyi.blog.domain.dto.PostVideoDto;
import com.ruoyi.blog.enums.BlogStatusEnum;
import com.ruoyi.blog.mapper.BlogMapper;
import com.ruoyi.blog.mapper.CourseMapper;
import com.ruoyi.blog.service.CourseService;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.mq.enums.BlogTypeEnum;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteFileService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private RemoteFileService remoteFileService;

    @Override
    public int postCourse(PostCourseDto dto) {
        Long userId = SecurityUtils.getUserId();

        Course course = new Course();
        course.setAuthorId(userId);
        course.setTitle(dto.getTitle());
        course.setCover_pic(dto.getCoverPic());

        int flag;

        if (dto.getCourseId() != null && dto.getCourseId() > 0) {
            course.setCourseId(dto.getCourseId());
            flag = courseMapper.updateCourse(course);
        } else {
            flag = courseMapper.insertCourse(course);
        }

        return flag;
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
