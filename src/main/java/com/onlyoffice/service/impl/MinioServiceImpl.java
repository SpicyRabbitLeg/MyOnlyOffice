package com.onlyoffice.service.impl;

import com.onlyoffice.config.MinioConfig;
import com.onlyoffice.service.MinioService;
import com.onlyoffice.utils.FileUtil;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MinioServiceImpl implements MinioService {
    private final Logger logger = LoggerFactory.getLogger(MinioServiceImpl.class);
    private final MinioClient minioClient;
    private final MinioConfig.MinioProperties minioProperties;

    public MinioServiceImpl(MinioClient minioClient, MinioConfig.MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    @Override
    public Boolean bucketExist(String bucket) {
        try {
            BucketExistsArgs build = BucketExistsArgs.builder().bucket(bucket).build();
            return minioClient.bucketExists(build);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean createBucket(String bucket) {
        if (!bucketExist(bucket)) {
            try {
                MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucket).build();
                minioClient.makeBucket(makeBucketArgs);
                return true;
            } catch (Exception e) {
                logger.error(e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public List<Bucket> listBucket() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>(0);
        }
    }

    @Override
    public Boolean deleteBucket(String bucket) {
        try {
            RemoveBucketArgs removeBucketArgs = RemoveBucketArgs.builder().bucket(bucket).build();
            minioClient.removeBucket(removeBucketArgs);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public String uploadFile(MultipartFile file, String bucket) {
        try {
            StringBuilder sbr = new StringBuilder();
            sbr.append(new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()));
            sbr.append(UUID.randomUUID().toString().replaceAll("-", ""));
            sbr.append(file.getOriginalFilename());
            String result = sbr.toString();
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucket)
                    .object(result)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            minioClient.putObject(putObjectArgs);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return StringUtils.EMPTY;
        }
    }

    @Override
    public void downloadObject(String bucket, String uri,String filename, String contentType) {
        Assert.isTrue(StringUtils.isNotBlank(bucket),"bucket is blank");
        Assert.isTrue(StringUtils.isNotBlank(uri),"uri is blank");
        Assert.isTrue(StringUtils.isNotBlank(contentType),"contentType is blank");
        Assert.isTrue(StringUtils.isNotBlank(filename),"filename is blank");
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = attributes.getResponse();
            response.setContentType(contentType);
            response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename,StandardCharsets.UTF_8.name()));
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder().object(uri).bucket(bucket).build());
            FileUtil.writeFile(inputStream,response.getOutputStream());
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("设置文件流数据时出现问题===---");
        }
    }

    @Override
    public String getUrl(String bucket, String uri) {
        try {
            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .method(Method.GET)
                    .object(uri)
                    .expiry(7, TimeUnit.DAYS).build();
            return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    @Override
    public Boolean deleteObject(String bucket, String uri) {
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucket).object(uri).build();
            minioClient.removeObject(removeObjectArgs);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
