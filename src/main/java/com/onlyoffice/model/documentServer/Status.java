package com.onlyoffice.model.documentServer;

public enum Status {
    EDITING(1),  // 1 - 文档正在编辑
    SAVE(2),  // 2 - 文档保存
    CORRUPTED(3),  // 3 - document saving error has occurred
    MUST_FORCE_SAVE(6);  // 6 - 文档正在编辑但是保存了文档;

    private int code;
    Status(final int codeParam) {
        this.code = codeParam;
    }
    public int getCode() {  // get document status
        return this.code;
    }
}
