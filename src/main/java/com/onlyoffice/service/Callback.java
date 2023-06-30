package com.onlyoffice.service;

import com.onlyoffice.service.impl.CallbackHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 处理文档服务器返回的数据
 */
public interface Callback {
    int handle(com.onlyoffice.dto.Callback body, Long fileId);
    int getStatus();

    @Autowired
    default void selfRegistration(CallbackHandler callbackHandler) {
        callbackHandler.register(getStatus(), this);
    }
}
