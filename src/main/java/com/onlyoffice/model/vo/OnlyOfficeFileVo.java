package com.onlyoffice.model.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class OnlyOfficeFileVo {
    @NotNull(message = "id不能为空",groups = Runnable.class)
    private Long id;
    @NotEmpty(message = "fileKey不能为空",groups = Flag.class)
    private String fileKey;
    @NotEmpty(message = "title不能为空",groups = Flag.class)
    private String title;
    @NotEmpty(message = "url不能为空",groups = Flag.class)
    private String url;
    private Long length;
    private String contentType;
    private String md5;


    public String getName() {
        if (StringUtils.isNotBlank(title)) {
            return title.split("\\.")[0];
        }
        return StringUtils.EMPTY;
    }

    public String getSuffix() {
        if (StringUtils.isNotBlank(title)) {
            String[] split = title.split("\\.");
            if (split.length > 1) {
                return split[1];
            }
        }
        return StringUtils.EMPTY;
    }
}
