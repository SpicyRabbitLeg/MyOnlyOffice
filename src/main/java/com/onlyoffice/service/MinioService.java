package com.onlyoffice.service;

import io.minio.messages.Bucket;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MinioService {

    /**
     * 校验桶存不存在
     * @return
     */
    Boolean bucketExist(String bucket);

    /**
     * 创建桶
     * @param bucket
     * @return
     */
    Boolean createBucket(String bucket);

    /**
     * 获取到所有的桶
     * @return
     */
    List<Bucket> listBucket();

    /**
     * 删除一个桶
     * @param bucket
     * @return
     */
    Boolean deleteBucket(String bucket);

    /**
     * 上传文件到服务器
     * @param file
     */
    String uploadFile(MultipartFile file,String bucket);


    /**
     * 获取文件，并下载
     * @param uri
     */
    void downloadObject(String bucket, String uri,String filename,String contentType);


    /**
     * 无需登录即可访问的图片，30天内有效
     * @param bucket
     * @param uri
     * @return
     */
    String getUrl(String bucket,String uri);


    /**
     * 删除桶下的文件
     * @param bucket
     * @param uri
     * @return
     */
    Boolean deleteObject(String bucket,String uri);
}
