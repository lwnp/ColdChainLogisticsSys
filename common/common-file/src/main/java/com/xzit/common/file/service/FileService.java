package com.xzit.common.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile multipartFile,String path);
    void deleteFile(String path);
}
