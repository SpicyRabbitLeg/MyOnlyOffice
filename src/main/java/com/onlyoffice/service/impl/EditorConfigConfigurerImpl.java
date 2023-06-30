package com.onlyoffice.service.impl;

import com.onlyoffice.config.OnlyOfficeProperties;
import com.onlyoffice.model.documentServer.Action;
import com.onlyoffice.model.documentServer.DefaultCustomizationWrapper;
import com.onlyoffice.model.documentServer.DefaultFileWrapper;
import com.onlyoffice.model.documentServer.EditorConfig;
import com.onlyoffice.service.EditorConfigConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EditorConfigConfigurerImpl implements EditorConfigConfigurer<DefaultFileWrapper> {
    @Autowired
    private OnlyOfficeProperties onlyOfficeProperties;
    @Autowired
    private CustomizationConfigurerImpl customizationConfigurer;

    @Override
    public void configure(EditorConfig editorConfig, DefaultFileWrapper wrapper) {
        // 不显示新建
        editorConfig.setTemplates(null);
        editorConfig.setCreateUrl(null);

        // 设置回调接口
        editorConfig.setCallbackUrl(onlyOfficeProperties.getCallbackUrl() + wrapper.getOnlyOfficeFile().getId());
        editorConfig.setLang("zh"); // 语言，写死不允许修改
        Boolean canEdit = wrapper.getCanEdit();
        Action action = wrapper.getAction();
        editorConfig.setCoEditing(action.equals(Action.view) ? new HashMap<String, Object>() {{
            put("mode", "strict");
            put("change", false);
        }} : new HashMap<String, Object>() {{
            put("mode", "fast");
            put("change", true);
        }});


        //  define the customization configurer
        customizationConfigurer.configure(editorConfig.getCustomization(),
                DefaultCustomizationWrapper.builder()
                        .action(action)
                        .build());

        // 设置Model
        editorConfig.setMode(canEdit && !action.equals(Action.view) ? EditorConfig.Mode.edit : EditorConfig.Mode.view);
        editorConfig.setUser(wrapper.getUser());
        // 设置embedded
        editorConfig.setEmbedded(null);
    }
}
