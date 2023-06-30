package com.onlyoffice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onlyoffice.config.OnlyOfficeProperties;
import com.onlyoffice.mapper.OnlyOfficeFileMapper;
import com.onlyoffice.model.OnlyOfficeFile;
import com.onlyoffice.model.documentServer.Config;
import com.onlyoffice.model.vo.OnlyOfficeFileVo;
import com.onlyoffice.service.OnlyOfficeFileService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


@Service
public class OnlyOfficeFileServiceImpl implements OnlyOfficeFileService {
    private final OnlyOfficeFileMapper onlyOfficeFileMapper;
    private final OnlyOfficeProperties onlyOfficeProperties;

    public OnlyOfficeFileServiceImpl (OnlyOfficeFileMapper onlyOfficeFileMapper,OnlyOfficeProperties onlyOfficeProperties){
        this.onlyOfficeFileMapper = onlyOfficeFileMapper;
        this.onlyOfficeProperties = onlyOfficeProperties;
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return onlyOfficeFileMapper.deleteById(ids.get(0));
    }

    @Override
    public OnlyOfficeFile updateById(OnlyOfficeFileVo onlyOfficeFileVo) {
        Assert.isTrue(onlyOfficeFileVo.getId() != null,"file id is null!");
        OnlyOfficeFile onlyOfficeFile = new OnlyOfficeFile();
        BeanUtils.copyProperties(onlyOfficeFileVo,onlyOfficeFile);
        String title = onlyOfficeFileVo.getTitle();
        if(StringUtils.isNotBlank(title)) {
            onlyOfficeFile.setName(onlyOfficeFileVo.getName());
            onlyOfficeFile.setSuffix(onlyOfficeFileVo.getSuffix());
        }
        return onlyOfficeFileMapper.updateById(onlyOfficeFile) > 0 ? onlyOfficeFile : null;
    }

    @Override
    public IPage<OnlyOfficeFile> queryByPage(Long current, Integer rows, OnlyOfficeFileVo onlyOfficeFileVo) {
        LambdaQueryWrapper<OnlyOfficeFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtils.isNotEmpty(onlyOfficeFileVo.getId()),OnlyOfficeFile::getId,onlyOfficeFileVo.getId());
        wrapper.eq(StringUtils.isNotBlank(onlyOfficeFileVo.getTitle()),OnlyOfficeFile::getTitle,onlyOfficeFileVo.getTitle());
        wrapper.eq(StringUtils.isNotBlank(onlyOfficeFileVo.getFileKey()),OnlyOfficeFile::getFileKey,onlyOfficeFileVo.getFileKey());
        return onlyOfficeFileMapper.selectPage(new Page<>(current, rows), wrapper);
    }

    @Override
    public List<OnlyOfficeFile> queryByIds(List<Long> ids) {
        LambdaQueryWrapper<OnlyOfficeFile> wrapper = new LambdaQueryWrapper<>();
        return new ArrayList<>();
    }

    @Override
    public OnlyOfficeFile queryById(Long id) {
        return onlyOfficeFileMapper.selectById(id);
    }

    @Override
    public OnlyOfficeFile saveOnlyOfficeFile(OnlyOfficeFileVo onlyOfficeFileVo) {
        OnlyOfficeFile onlyOfficeFile = new OnlyOfficeFile();
        BeanUtils.copyProperties(onlyOfficeFileVo,onlyOfficeFile);
        onlyOfficeFile.setId(null);
        if(StringUtils.isNotBlank(onlyOfficeFileVo.getTitle())) {
            onlyOfficeFile.setName(onlyOfficeFileVo.getName());
            onlyOfficeFile.setSuffix(onlyOfficeFileVo.getSuffix());
        }
        return onlyOfficeFileMapper.insert(onlyOfficeFile) > 0 ? onlyOfficeFile : null;
    }

    @Override
    public Config buildConfigByFileId(Long fileId) {
        Config config = new Config();


        return config;
    }
}
