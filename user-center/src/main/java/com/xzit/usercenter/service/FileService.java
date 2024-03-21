package com.xzit.usercenter.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile multipartFile,String path);
    void deleteFile(String path);
}
