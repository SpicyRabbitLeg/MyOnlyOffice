package com.onlyoffice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.onlyoffice.config.MinioConfig;
import com.onlyoffice.config.OnlyOfficeProperties;
import com.onlyoffice.model.OnlyOfficeFile;
import com.onlyoffice.model.documentServer.Action;
import com.onlyoffice.model.documentServer.Config;
import com.onlyoffice.model.documentServer.DefaultFileWrapper;
import com.onlyoffice.model.documentServer.EditorConfig;
import com.onlyoffice.model.vo.OnlyOfficeFileVo;
import com.onlyoffice.service.FileConfigurer;
import com.onlyoffice.service.MinioService;
import com.onlyoffice.service.OnlyOfficeFileService;
import com.onlyoffice.utils.MD5Util;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    private OnlyOfficeFileService onlyOfficeFileService;
    @Autowired
    private MinioService minioService;
    @Autowired
    private MinioConfig.MinioProperties minioProperties;
    @Autowired
    private MD5Util md5Util;
    @Autowired
    private OnlyOfficeProperties onlyOfficeProperties;
    @Autowired
    private FileConfigurer<DefaultFileWrapper> fileConfigurer;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("hh", "Hello World OnlyOffice");
        IPage<OnlyOfficeFile> onlyOfficeFileIPage = onlyOfficeFileService.queryByPage(0L, 100, new OnlyOfficeFileVo());
        model.addAttribute("files",onlyOfficeFileIPage.getRecords());
        return "onlyoffice/index";
    }


    /**
     * 编辑页面
     */
    @GetMapping("/edit/{fileId}")
    public String edit2(@PathVariable("fileId") Long fileId,
                        @RequestParam(value = "type", defaultValue = "desktop") String type,
                        @RequestParam(value = "mode",defaultValue = "view") String mode,
                        Model model) {
        OnlyOfficeFile onlyOfficeFile = onlyOfficeFileService.queryById(fileId);
        Assert.isTrue(ObjectUtils.isNotEmpty(onlyOfficeFile), "file is not fond!");
        Action action = mode.equals("view") ? Action.view : Action.edit;

        Config config = fileConfigurer.getFileModel(DefaultFileWrapper.builder()
                .fileName(onlyOfficeFile.getTitle())
                .onlyOfficeFile(onlyOfficeFile)
                .action(action)
                .user(getUser())
                .type(type)
                .build());
        model.addAttribute("config", config);
        model.addAttribute("api", onlyOfficeProperties.getApi());
        return "onlyoffice/edit";
    }

    /**
     * 上传文件
     */
    @Transactional
    @ResponseBody
    @PostMapping("/uploadFile")
    public R<OnlyOfficeFile> uploadFile(@PathVariable("file") MultipartFile file) throws Exception {
        String uri = minioService.uploadFile(file, minioProperties.getBucket());
        OnlyOfficeFileVo onlyOfficeFileVo = new OnlyOfficeFileVo();
        onlyOfficeFileVo.setTitle(file.getOriginalFilename());
        onlyOfficeFileVo.setContentType(file.getContentType());
        onlyOfficeFileVo.setLength(file.getSize());
        onlyOfficeFileVo.setUrl(uri);
        String encrypt = md5Util.encrypt(file.getBytes());
        onlyOfficeFileVo.setMd5(encrypt);
        onlyOfficeFileVo.setFileKey(md5Util.key(encrypt));
        return R.ok(onlyOfficeFileService.saveOnlyOfficeFile(onlyOfficeFileVo));
    }

    @GetMapping("/downloadFile/{fileId}")
    public void downloadFile(@PathVariable("fileId") Long fileId, HttpServletResponse response) throws Exception {
        OnlyOfficeFile onlyOfficeFile = onlyOfficeFileService.queryById(fileId);
        if (ObjectUtils.isNotEmpty(onlyOfficeFile)) {
            minioService.downloadObject(minioProperties.getBucket(), onlyOfficeFile.getUrl(), onlyOfficeFile.getTitle(), onlyOfficeFile.getContentType());
        } else {
            response.getWriter().write("error");
        }
    }


    /**
     * 根据当前登录人设置登录人、名称、分组      本系统只接入编辑与协同
     */
    private EditorConfig.User getUser() {
        EditorConfig.User user = new EditorConfig.User();
        user.setFavorite(true);
        user.setId("1");
        user.setName("德玛西亚");
        user.setGroup("default");
        return user;
    }

}
