package com.onlyoffice.service;

public interface Configurer<O, W> {
    void configure(O instance, W wrapper);
}
