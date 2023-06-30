package com.onlyoffice.service;

import com.onlyoffice.model.documentServer.Config;

public interface FileConfigurer<W> extends Configurer<Config, W>{
    void configure(Config model, W wrapper);
    Config getFileModel(W wrapper);
}
