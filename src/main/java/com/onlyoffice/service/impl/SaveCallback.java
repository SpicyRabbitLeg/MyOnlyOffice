package com.onlyoffice.service.impl;

import com.onlyoffice.config.MinioConfig;
import com.onlyoffice.model.OnlyOfficeFile;
import com.onlyoffice.model.documentServer.Status;
import com.onlyoffice.model.vo.OnlyOfficeFileVo;
import com.onlyoffice.service.Callback;
import com.onlyoffice.service.MinioService;
import com.onlyoffice.service.OnlyOfficeFileService;
import com.onlyoffice.utils.HttpUtil;
import com.onlyoffice.utils.MD5Util;
import com.onlyoffice.utils.MockMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SaveCallback implements Callback {
    @Autowired
    private OnlyOfficeFileService onlyOfficeFileService;
    @Autowired
    private MinioService minioService;
    @Autowired
    private MinioConfig.MinioProperties minioProperties;
    @Autowired
    private MD5Util md5Util;

    @Override
    public int handle(com.onlyoffice.dto.Callback body, Long fileId) {
        OnlyOfficeFile onlyOfficeFile = onlyOfficeFileService.queryById(fileId);
        byte[] bytes = HttpUtil.downloadFile(body.getUrl(), null);
        MultipartFile multipartFile = new MockMultipartFile(onlyOfficeFile.getTitle(), onlyOfficeFile.getTitle(), onlyOfficeFile.getContentType(), bytes);
        String url = minioService.uploadFile(multipartFile, minioProperties.getBucket());

        OnlyOfficeFileVo temp = new OnlyOfficeFileVo();
        temp.setId(onlyOfficeFile.getId());
        temp.setUrl(url);
        String md5 = md5Util.encrypt(bytes);
        temp.setMd5(md5);
        temp.setFileKey(md5Util.key(md5));
        temp.setTitle(onlyOfficeFile.getTitle());
        onlyOfficeFileService.updateById(temp);
        // 历史记录信息
        return 0;
    }

    @Override
    public int getStatus() {
        return Status.SAVE.getCode();
    }
}
