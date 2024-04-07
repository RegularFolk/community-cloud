package com.ruoyi.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadVod(MultipartFile file);
}
