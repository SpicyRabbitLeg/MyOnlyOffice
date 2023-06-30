package com.onlyoffice.model.documentServer;

import com.onlyoffice.model.OnlyOfficeFile;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefaultDocumentWrapper {
    private Document.Permission permission;
    private String fileName;
    private Boolean favorite;
    private Boolean isEnableDirectUrl;
    private OnlyOfficeFile onlyOfficeFile;
}
