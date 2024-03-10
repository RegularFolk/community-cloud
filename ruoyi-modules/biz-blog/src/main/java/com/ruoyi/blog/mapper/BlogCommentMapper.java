package com.ruoyi.blog.mapper;

import com.ruoyi.blog.domain.BlogComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogCommentMapper {

    int getParentCommentCnt(@Param("blogId") Long blogId);

    /**
     * order 入参参考 com/ruoyi/blog/enums/CommentOrderEnum.java
     */
    List<BlogComment> getParentCommentPartly(@Param("blogId") Long blogId,
                                             @Param("start") Long start,
                                             @Param("commentStep") Long commentStep,
                                             @Param("order") String order);

    List<BlogComment> getSubComment(@Param("blogId") Long blogId,
                                    @Param("parentCommentIds") List<Long> parentCommentIds);


    int putComment(BlogComment comment);

    List<BlogComment> selectCommentByIds(@Param("idList") List<Long> idList);
}
