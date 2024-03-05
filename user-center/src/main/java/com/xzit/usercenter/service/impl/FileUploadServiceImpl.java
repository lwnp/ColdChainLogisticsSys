package com.xzit.usercenter.service.impl;

import com.xzit.common.sys.Exception.BizException;
import com.xzit.common.sys.utils.FileUtil;
import com.xzit.usercenter.config.properties.MinioProperties;
import com.xzit.usercenter.service.FileUploadService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final MinioProperties minioProperties;
    private MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }
    public Boolean exists(String filePath) {
        boolean exist = true;
        try {
            getMinioClient()
                    .statObject(StatObjectArgs.builder().bucket(minioProperties.getBucketName()).object(filePath).build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    @SneakyThrows
    public void upload(String path, String fileName, InputStream inputStream) {
        getMinioClient().putObject(
                PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(path + fileName).stream(
                                inputStream, inputStream.available(), -1)
                        .build());
    }
    public String getFileAccessUrl(String filePath) {
        return minioProperties.getUrl() + filePath;
    }
    @Override
    @SuppressWarnings("all")
    public String uploadFile(MultipartFile file,String path) {
        try {
            String md5 = FileUtil.getMd5(file.getInputStream());
            String extName = FileUtil.getExtName(file.getOriginalFilename());
            String fileName = md5 + extName;
            if (!exists(path + fileName)) {
                upload(path, fileName, file.getInputStream());
            }
            return getFileAccessUrl(path + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("文件上传失败");
        }
    }
}
