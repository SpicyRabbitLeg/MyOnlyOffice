package com.onlyoffice.model.documentServer;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 针对DocEditor需要的配置项
 */
@Data
public class Config {
    // 文档配置项目
    private Document document;
    // 编辑类型
    private DocumentType documentType;
    // 文档编辑器定制化配置项
    private EditorConfig editorConfig;
    //token
    private String token;
    //显示类型
    private String type;


    public static class ConfigBuildr{
        // 文件名称
        private String filename;
        // 文件后缀
        private String fileSuffix;
    }


    public static Config build(){
        Config config = new Config();
        EditorConfig editorConfig = new EditorConfig();
        Document document = new Document();
        EditorConfig.Customization customization = new EditorConfig.Customization();
        EditorConfig.Customization.Goback goBack = new EditorConfig.Customization.Goback();
        EditorConfig.Customization.Logo logo = new EditorConfig.Customization.Logo();
        editorConfig.setTemplates(new ArrayList<>(1));
        editorConfig.setCoEditing(new HashMap<>());
        customization.setLogo(logo);
        customization.setGoback(goBack);
        editorConfig.setCustomization(customization);
        config.setEditorConfig(editorConfig);
        document.setInfo(new Document.Info());
        config.setDocument(document);
        return config;
    }
}
