package com.onlyoffice.utils;

import com.onlyoffice.model.documentServer.DocumentType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FileUtility {
    private List<String> extsDocument = Arrays.asList(
            ".doc", ".docx", ".docm",
            ".dot", ".dotx", ".dotm",
            ".odt", ".fodt", ".ott", ".rtf", ".txt",
            ".html", ".htm", ".mht", ".xml",
            ".pdf", ".djvu", ".fb2", ".epub", ".xps", ".oform");

    private List<String> extsSpreadsheet = Arrays.asList(
            ".xls", ".xlsx", ".xlsm", ".xlsb",
            ".xlt", ".xltx", ".xltm",
            ".ods", ".fods", ".ots", ".csv");

    private List<String> extsPresentation = Arrays.asList(
            ".pps", ".ppsx", ".ppsm",
            ".ppt", ".pptx", ".pptm",
            ".pot", ".potx", ".potm",
            ".odp", ".fodp", ".otp");


    /**
     * 根据后缀名称返回文档类型
     */
    public DocumentType getDocumentType(String suffix) {
        suffix = suffix.toLowerCase();
        if (extsDocument.contains(suffix)) {
            return DocumentType.word;
        }
        if (extsSpreadsheet.contains(suffix)) {
            return DocumentType.cell;
        }
        if (extsPresentation.contains(suffix)) {
            return DocumentType.slide;
        }
        return DocumentType.word;
    }
}
