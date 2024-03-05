package com.xzit.usercenter.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String uploadFile(MultipartFile multipartFile,String path);
}
