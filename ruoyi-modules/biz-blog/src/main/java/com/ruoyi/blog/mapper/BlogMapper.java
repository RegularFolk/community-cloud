package com.ruoyi.blog.mapper;


import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.BlogCollected;
import com.ruoyi.blog.domain.BlogContent;
import com.ruoyi.blog.domain.PersonalClassification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper {


    /**
     * 临时测试方法，获取博客列表
     * @return
     */
    List<Blog> getTempBlogList();


    void insertBlog(Blog blog);

    void insertBlogContent(BlogContent blogContent);

    int insertPersonClassification(PersonalClassification ac);

    int deletePersonClassificationById(Long id);

    int deleteBlogById(Long id);

    int deleteContentById(Long id);

    List<Blog> getArticleList(@Param("blog") Blog blog,
                              @Param("pageSize") Integer pageSize,
                              @Param("offset") Integer offset);

    List<PersonalClassification> getPersonClassByIds(@Param("idList") List<Long> idList);

    int deleteBlogByIds(@Param("idList") List<Long> idList);

    void deleteContentByIds(@Param("idList") List<Long> idList);

    int moveToDefaultClass(@Param("userId") Long userId, @Param("articleIdList") List<Long> articleIdList);

    List<PersonalClassification> getPersonClassByUserId(Long userId);

    long getArticleCnt(Blog blog);

    BlogContent getArticleContent(Long articleId);

    int updateBlogById(Blog blog);

    int updateBlogContent(BlogContent blogContent);
}
