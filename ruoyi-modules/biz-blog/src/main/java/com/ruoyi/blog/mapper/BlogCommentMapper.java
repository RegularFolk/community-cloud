package com.ruoyi.blog.mapper;

import com.ruoyi.blog.domain.BlogComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogCommentMapper {

    int getParentCommentCnt(@Param("blogId") Long blogId);

    List<BlogComment> getParentCommentPartly(@Param("blogId") Long blogId, @Param("start") Long start, @Param("commentStep") Integer commentStep);

    List<BlogComment> getSubComment(@Param("blogId") Long blogId, @Param("parentCommentIds") List<Long> parentCommentIds);


}
