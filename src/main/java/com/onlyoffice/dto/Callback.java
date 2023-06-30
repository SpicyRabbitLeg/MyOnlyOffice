package com.onlyoffice.dto;

import com.onlyoffice.model.documentServer.Action;
import com.onlyoffice.model.documentServer.EditorConfig;
import lombok.Data;

import java.util.List;

/**
 * 文档服务器callback数据
 */
@Data
public class Callback {
    // 文件类型 docx
    private String filetype;
    // 最新文档下载地址
    private String url;
    // 唯一key值
    private String key;
    // ...
    private String changesurl;
    // 历史记录
    private History history;
    // token
    private String token;
    // 定义执行强制保存请求时启动器的类型
    private Integer forcesavetype;
    // 当前的操作状态
    private Integer status;
    //全部用户存储用户id
    private List<String> users;
    // 只有当前操作人的信息
    private List<Action> actions;
    //
    private String userdata;
    //
    private String lastsave;
    private Boolean notmodified;

    @Data
    public static class History {
        private String serverVersion;
        private String key;
        private Integer version;
        private String created;
        private EditorConfig.User user;
//        private List<ChangesHistory> changes;
    }

    @Data
    public static class Action {
        // 用户id
        private String userid;
        // 操作类型
        private com.onlyoffice.model.documentServer.Action type;
    }

}
