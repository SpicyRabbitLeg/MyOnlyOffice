package com.onlyoffice.service;

import com.onlyoffice.model.documentServer.Document;

public interface DocumentConfigurer <W> extends Configurer<Document, W> {
    void configure(Document document, W wrapper);
}
