package com.onlyoffice.service.impl;

import com.onlyoffice.service.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CallbackHandler {
    private final Logger logger = LoggerFactory.getLogger(CallbackHandler.class);
    private final Map<Integer, Callback> callbackHandlers = new HashMap<>();

    public void register(int code, Callback callback) {
        callbackHandlers.put(code, callback);
    }


    public int handle(com.onlyoffice.dto.Callback body, Long fileId) {
        Callback callback = callbackHandlers.get(body.getStatus());
        if (callback == null) {
            logger.warn("Callback status " + body.getStatus() + " is not supported yet");
            return 0;
        }
        return callback.handle(body, fileId);
    }
}
