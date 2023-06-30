package com.onlyoffice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.onlyoffice.model.OnlyOfficeFile;
import com.onlyoffice.model.documentServer.Config;
import com.onlyoffice.model.vo.OnlyOfficeFileVo;

import java.util.List;

public interface OnlyOfficeFileService {

    Integer removeByIds(List<Long> ids);

    OnlyOfficeFile updateById(OnlyOfficeFileVo onlyOfficeFileVo);

    IPage<OnlyOfficeFile> queryByPage(Long current, Integer rows, OnlyOfficeFileVo onlyOfficeFileVo);

    List<OnlyOfficeFile> queryByIds(List<Long> ids);

    OnlyOfficeFile queryById(Long id);

    OnlyOfficeFile saveOnlyOfficeFile(OnlyOfficeFileVo onlyOfficeFileVo);

    /**
     * 根据文件的id构建文档编辑器需要的config对象
     */
    Config buildConfigByFileId(Long fileId);

}
