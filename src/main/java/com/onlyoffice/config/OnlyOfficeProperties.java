package com.onlyoffice.config;

import com.onlyoffice.model.documentServer.EditorConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ConfigurationProperties(prefix = "office")
@Component
public class OnlyOfficeProperties {
    // 本地服务ip地址
    private String ip;

    /**
     * 编辑器默认语言【只允许中文！】
     */
    private String lang;
    /**
     * logo信息
     */
    private EditorConfig.Customization.Logo logo;

    /**
     * 文档服务器回调接口【交互接口非常非常重要】
     *
     */
    private String callbackUrl;

    private String downloadUrl;
    private String goBack;

    /**
     * 文档服务器js文件
     */
    private String api;

    // 可编辑、查看、转换的文件类型
    private List<String> viewedDocs;
    private List<String> editedDocs;
    private List<String> convertDocs;


    public Set<String> getFillExts(){
        HashSet<String> result = new HashSet<>();
        result.addAll(viewedDocs);
        result.addAll(editedDocs);
        result.addAll(convertDocs);
        return result;
    }

    public String getCallbackUrl(){
        return ip + callbackUrl;
    }

    public String getDownloadUrl(){
        return ip + downloadUrl;
    }
}
