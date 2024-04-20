package com.xzit.common.file.service.impl;


import com.xzit.common.file.config.properties.MinioProperties;
import com.xzit.common.file.service.FileService;
import com.xzit.common.file.utils.FileUtil;
import com.xzit.common.sys.exception.BizException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final MinioProperties minioProperties;
    @Value("${upload.minio.url}")
    private String path_prefix;
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

    @Override
    public void deleteFile(String path) {
        try{
            getMinioClient().removeObject(RemoveObjectArgs.builder()
                            .bucket("coldchainsys")
                            .object(path.replace(path_prefix,""))
                    .build());
        } catch (Exception e){
            throw new BizException("文件删除失败");
        }
    }
}
