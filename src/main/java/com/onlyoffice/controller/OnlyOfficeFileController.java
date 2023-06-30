package com.onlyoffice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.onlyoffice.model.OnlyOfficeFile;
import com.onlyoffice.model.vo.Flag;
import com.onlyoffice.model.vo.OnlyOfficeFileVo;
import com.onlyoffice.service.OnlyOfficeFileService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/file")
public class OnlyOfficeFileController {
    private final OnlyOfficeFileService onlyOfficeFileService;

    public OnlyOfficeFileController(OnlyOfficeFileService onlyOfficeFileService){
        this.onlyOfficeFileService = onlyOfficeFileService;
    }


    @GetMapping("/{id}")
    public R<OnlyOfficeFile> queryById(@PathVariable("id") Long id){
        OnlyOfficeFile onlyOfficeFile = onlyOfficeFileService.queryById(id);
        if(ObjectUtils.isEmpty(onlyOfficeFile)) {
            return R.failed("query file is not fond by id: " + id);
        }
        return R.ok(onlyOfficeFile);
    }

    @PostMapping("/ids")
    public R<List<OnlyOfficeFile>> queryByIds(@RequestParam("ids") List<Long> ids){
        List<OnlyOfficeFile> items = onlyOfficeFileService.queryByIds(ids);
        if(CollectionUtils.isEmpty(items)) {
            return R.failed("query files is not fond by ids: " + ids);
        }
        return R.ok(items);
    }

    @PostMapping("")
    public R<OnlyOfficeFile> saveOnlyOfficeFile(@Validated(Flag.class) @RequestBody OnlyOfficeFileVo onlyOfficeFileVo){
        OnlyOfficeFile onlyOfficeFile = onlyOfficeFileService.saveOnlyOfficeFile(onlyOfficeFileVo);
        if(ObjectUtils.isEmpty(onlyOfficeFile)) {
            return R.failed("save OnlyOfficeFile is error");
        }
        return R.ok(onlyOfficeFile);
    }

    @PostMapping("/{current}/{rows}")
    public R<IPage<OnlyOfficeFile>> queryByPage(@PathVariable("current") Long current,
                                               @PathVariable("rows") Integer rows,
                                               @RequestBody OnlyOfficeFileVo onlyOfficeFileVo){
        IPage<OnlyOfficeFile> result = onlyOfficeFileService.queryByPage(current,rows,onlyOfficeFileVo);
        if(CollectionUtils.isEmpty(result.getRecords())) {
            return R.failed("query files is not found");
        }
        return R.ok(result);
    }


    @PutMapping("")
    public R<OnlyOfficeFile> updateById(@Validated(Runnable.class)  @RequestBody OnlyOfficeFileVo onlyOfficeFileVo){
        OnlyOfficeFile onlyOfficeFile = onlyOfficeFileService.updateById(onlyOfficeFileVo);
        if(ObjectUtils.isEmpty(onlyOfficeFile)) {
            return R.failed("update file is empty");
        }
        return R.ok(onlyOfficeFile);
    }

    @DeleteMapping("")
    public R<Integer> removeByIds(@RequestParam("ids") List<Long> ids){
        Integer count = onlyOfficeFileService.removeByIds(ids);
        if(count < 0) {
            return R.failed("delete empty");
        }
        return R.ok(count);
    }

}

