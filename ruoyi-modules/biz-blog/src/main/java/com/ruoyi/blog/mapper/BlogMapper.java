package com.ruoyi.blog.mapper;


import com.ruoyi.blog.domain.Blog;
import com.ruoyi.blog.domain.BlogCollected;
import com.ruoyi.blog.domain.BlogContent;
import com.ruoyi.blog.domain.PersonalClassification;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    int deleteBlogById(@Param("id") Long id, @Param("authorId")Long authorId);

    int deleteContentById(Long id);

    /**
     * Blog 表通用分页查询方法
     */
    List<Blog> getArticleList(@Param("blog") Blog blog,
                              @Param("pageSize") Integer pageSize,
                              @Param("offset") Integer offset,
                              @Param("order") Integer order);

    /**
     * ids查询通用方法
     */
    List<Blog> getBlogByIds(@Param("idList") List<Long> idList);

    List<PersonalClassification> getPersonClassByIds(@Param("idList") List<Long> idList);

    int deleteBlogByIds(@Param("idList") List<Long> idList);

    void deleteContentByIds(@Param("idList") List<Long> idList);

    int moveToDefaultClass(@Param("userId") Long userId, @Param("articleIdList") List<Long> articleIdList);

    List<PersonalClassification> getPersonClassByUserId(Long userId);

    /**
     * Blog 表通用计数方法
     */
    long getArticleCnt(Blog blog);

    BlogContent getArticleContent(Long articleId);

    int updateBlogById(Blog blog);

    int updateBlogContent(BlogContent blogContent);

    List<Map<String, String>> getAnsCntByIds(@Param("qtnIdList") List<Long> qtnIdList);

    List<BlogContent> getContentByIds(@Param("idList") List<Long> idList);

    int acceptAns(@Param("ansId") Long ansId, @Param("qtnId") Long qtnId, @Param("userId") Long userId);

    int getAcptAnsCnt(@Param("qtnId") Long qtnId);
}
