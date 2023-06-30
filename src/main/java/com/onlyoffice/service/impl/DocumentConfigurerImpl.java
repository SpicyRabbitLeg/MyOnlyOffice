package com.onlyoffice.service.impl;

import com.onlyoffice.config.OnlyOfficeProperties;
import com.onlyoffice.model.documentServer.DefaultDocumentWrapper;
import com.onlyoffice.model.documentServer.Document;
import com.onlyoffice.service.DocumentConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentConfigurerImpl implements DocumentConfigurer<DefaultDocumentWrapper> {
    @Autowired
    private OnlyOfficeProperties onlyOfficeProperties;

    @Override
    public void configure(Document document, DefaultDocumentWrapper wrapper) {
        String fileName = wrapper.getFileName();
        Document.Permission permission = wrapper.getPermission();
        // 设置文件名称
        document.setTitle(fileName);
        // 设置访问地址
        document.setUrl(onlyOfficeProperties.getDownloadUrl() + wrapper.getOnlyOfficeFile().getId());
        document.setFileType(wrapper.getOnlyOfficeFile().getSuffix());
        Document.Info info = document.getInfo();
        info.setFavorite(wrapper.getFavorite());
        info.setOwner(wrapper.getOnlyOfficeFile().getCreater());
        info.setUploaded(wrapper.getOnlyOfficeFile().getCreateTime().toString());
        document.setKey(wrapper.getOnlyOfficeFile().getFileKey());
        document.setPermissions(permission);
    }
}
