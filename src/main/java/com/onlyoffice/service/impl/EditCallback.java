package com.onlyoffice.service.impl;

import com.onlyoffice.model.documentServer.Status;
import com.onlyoffice.service.Callback;
import org.springframework.stereotype.Service;

@Service
public class EditCallback implements Callback {
    @Override
    public int handle(com.onlyoffice.dto.Callback body, Long fileId) {
        return 0;
    }

    @Override
    public int getStatus() {
        return Status.EDITING.getCode();
    }
}
