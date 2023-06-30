package com.onlyoffice.service;

import com.onlyoffice.model.documentServer.EditorConfig;

public interface EditorConfigConfigurer<W> extends Configurer<EditorConfig, W> {
    void configure(EditorConfig editorConfig, W wrapper);
}
