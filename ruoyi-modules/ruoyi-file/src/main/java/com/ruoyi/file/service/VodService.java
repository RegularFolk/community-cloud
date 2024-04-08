package com.ruoyi.file.service;

import com.ruoyi.common.core.domain.IdDto;
import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadVod(MultipartFile file);

    String getVodUrl(String vodId);

    int delVod(String vodId);
}
