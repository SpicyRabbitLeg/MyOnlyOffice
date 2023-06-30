package com.onlyoffice.model.documentServer;

import lombok.Data;

import java.util.List;

@Data
public class Document {
    // 文档的附加参数(文档所有者、存储文档的文件夹、上传日期，共享设置)
    private Info info;
    // 文档是否被编辑和下载的权限
    private Permission permissions;
    // 文件类型 docx ppt
    private String fileType;
    // 文档服务器唯一标识
    private String key;
    // 文件名称
    private String title;
    // 文档服务器下载文件的地址
    private String url;

    @Data
    public static class Info {
        // 文件创建人的名称
        private String owner;
        // 收藏图标的高亮显示状态
        private Boolean favorite;
        // 文件上传日期
        private String uploaded;
    }

    @Data
    public static class Permission {
        // 文档注释功能
        private Boolean comment = true;
        // 文档注释权限控制功能，根据用户组
        private CommentGroup commentGroup;
        // 文档copy功能
        private Boolean copy = true;
        // 文档下载功能
        private Boolean download = true;
        // 文档编辑功能（在model为view时这个设置不起作用）
        private Boolean edit = true;
        // 文档打印功能
        private Boolean print = true;
        // 文档填写表单功能
        private Boolean fillForms = true;
        // 文档过滤器功能
        private Boolean modifyFilter = true;
        // 文档更改内容控件设置功能
        private Boolean modifyContentControl = true;
        // 文档审阅模式功能
        private Boolean review = true;
        // 文档聊天功能
        private Boolean chat = true;

        // 定义用户可以接受/拒绝其更改的组
        private List<String> reviewGroups;
        private List<String> userInfoGroups;


        @Data
        public static class CommentGroup{
            private List<String> view;
            private List<String> edit;
            private List<String> remove;
        }
    }
}
