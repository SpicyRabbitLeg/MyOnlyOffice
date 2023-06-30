package com.onlyoffice.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OnlyOfficeFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件唯一标识
     */
    private String fileKey;

    /**
     * 文件名称
     */
    private String title;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 文件content-type
     */
    private String contentType;

    /**
     * 文件大小
     */
    private Long length;

    /**
     * 文件真实地址
     */
    private String url;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String creater;

    /**
     * 跟新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 跟新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    /**
     * 状态
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer status;

    /**
     * 版本
     */
    @Version
    private Integer version;

    /**
     * md5值
     */
    private String md5;

    /**
     * 过期时间
     */
    private Long expiry;
}
