package com.onlyoffice.model.documentServer;

public enum Action {
    edit, // 编辑
    review, // 编辑并开启审阅模式
    view,  // 查看
    embedded, // 嵌入式
    filter,
    comment, // 查看并且开启审阅模式
    chat,
    fillForms,
    blockcontent
}
