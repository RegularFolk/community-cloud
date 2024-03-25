package com.ruoyi.blog.service;

import com.ruoyi.blog.domain.dto.PostQtnDto;
import com.ruoyi.blog.domain.dto.QtnAnsAcptDto;
import com.ruoyi.blog.domain.dto.QtnAnsListDto;
import com.ruoyi.blog.domain.dto.QtnSquareDto;
import com.ruoyi.blog.domain.vo.ArticleVo;
import com.ruoyi.blog.domain.vo.QtnAnsVo;
import com.ruoyi.blog.domain.vo.QtnSquareVo;
import com.ruoyi.common.core.domain.IdDto;
import com.ruoyi.common.mq.enums.BlogTypeEnum;

import java.util.List;

public interface QtnService {
    Long postQtn(PostQtnDto dto);

    int delQtn(IdDto dto);

    List<QtnSquareVo> list(QtnSquareDto dto);

    int collect(IdDto dto, BlogTypeEnum typeEnum);

    ArticleVo detail(IdDto dto);

    QtnAnsVo ansList(QtnAnsListDto dto);

    int accept(QtnAnsAcptDto dto);
}
