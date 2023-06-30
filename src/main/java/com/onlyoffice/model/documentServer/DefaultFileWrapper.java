package com.onlyoffice.model.documentServer;

import com.onlyoffice.model.OnlyOfficeFile;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefaultFileWrapper {
    private String fileId;
    private String fileName;
    private String type;
    private EditorConfig.User user;
    private String lang;
    private Action action;
    private String actionData;
    private Boolean canEdit;
    private Boolean isEnableDirectUrl;
    private OnlyOfficeFile onlyOfficeFile;
}
