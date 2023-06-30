package com.onlyoffice.service.impl;

import com.onlyoffice.config.OnlyOfficeProperties;
import com.onlyoffice.model.documentServer.DefaultCustomizationWrapper;
import com.onlyoffice.model.documentServer.EditorConfig;
import com.onlyoffice.service.CustomizationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomizationConfigurerImpl implements CustomizationConfigurer<DefaultCustomizationWrapper> {
    @Autowired
    private OnlyOfficeProperties onlyOfficeProperties;

    @Override
    public void configure(EditorConfig.Customization customization, DefaultCustomizationWrapper wrapper) {
        customization.setSubmitForm(false);
        customization.setLogo(onlyOfficeProperties.getLogo());
        EditorConfig.Customization.Goback goback = new EditorConfig.Customization.Goback();
        goback.setUrl(onlyOfficeProperties.getGoBack());
        customization.setGoback(goback);
    }
}
