package com.onlyoffice.service;

import com.onlyoffice.model.documentServer.EditorConfig;

public interface CustomizationConfigurer <W> extends Configurer<EditorConfig.Customization, W> {
    void configure(EditorConfig.Customization customization, W wrapper);
}
