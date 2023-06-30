package com.onlyoffice.controller;

import com.onlyoffice.dto.Callback;
import com.onlyoffice.service.impl.CallbackHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CallBackController {
    private final Logger logger = LoggerFactory.getLogger(CallBackController.class);
    @Autowired
    private CallbackHandler callbackHandler;

    @PostMapping("/callback")
    @ResponseBody
    public String track(HttpServletRequest request,
                        @RequestParam("fileId") Long fileId,
                        @RequestBody Callback body) {
        logger.info("============fileId:{} ===callback==={}",fileId,body);
        int error = callbackHandler.handle(body, fileId);
        return "{\"error\":" + error + "}";
    }
}
