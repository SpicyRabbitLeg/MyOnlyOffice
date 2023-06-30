package com.onlyoffice.service.impl;

import com.onlyoffice.config.OnlyOfficeProperties;
import com.onlyoffice.model.documentServer.*;
import com.onlyoffice.service.FileConfigurer;
import com.onlyoffice.utils.FileUtility;
import com.onlyoffice.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class FileConfigurerImpl implements FileConfigurer<DefaultFileWrapper> {
    @Autowired
    private FileUtility fileUtility;
    @Autowired
    private OnlyOfficeProperties onlyOfficeProperties;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private DocumentConfigurerImpl documentConfigurer;
    @Autowired
    private EditorConfigConfigurerImpl editorConfigConfigurer;

    @Override
    public void configure(Config config, DefaultFileWrapper wrapper) {
        String fileName = wrapper.getOnlyOfficeFile().getTitle();
        Action action = wrapper.getAction();

        // 获取页面显示类型
        DocumentType documentType = fileUtility.getDocumentType(wrapper.getOnlyOfficeFile().getSuffix());
        config.setDocumentType(documentType);
        config.setType(wrapper.getType());

        // TODO 根据用户设置权限
        Document.Permission userPermissions = new Document.Permission();

        // 查看是否支持编辑
        String fileExt = "." + wrapper.getOnlyOfficeFile().getSuffix();
        Boolean canEdit = onlyOfficeProperties.getEditedDocs().contains(fileExt);
        if ((!canEdit && action.equals(Action.edit) || action.equals(Action.fillForms)) && onlyOfficeProperties
                .getFillExts().contains(fileExt)) {
            canEdit = true;
            wrapper.setAction(Action.fillForms);
        }
        wrapper.setCanEdit(canEdit);


        DefaultDocumentWrapper documentWrapper = DefaultDocumentWrapper.builder()
                .fileName(fileName)
                .permission(updatePermissions(userPermissions, action, canEdit))
                .favorite(wrapper.getUser().getFavorite())
                .onlyOfficeFile(wrapper.getOnlyOfficeFile())
                .build();

        documentConfigurer.configure(config.getDocument(),documentWrapper);
        editorConfigConfigurer.configure(config.getEditorConfig(),wrapper);

        HashMap<String, Object> params = new HashMap<>();
        params.put("type", config.getType());
        params.put("documentType", config.getDocumentType());
        params.put("document", config.getDocument());
        params.put("editorConfig", config.getEditorConfig());
        config.setToken(jwtUtil.onlyOfficeCreateToken(params));
    }

    @Override
    public Config getFileModel(DefaultFileWrapper wrapper) {
        Config config = Config.build();
        configure(config, wrapper);
        return config;
    }


    private Document.Permission updatePermissions(final Document.Permission userPermissions, final Action action, final Boolean canEdit) {
        userPermissions.setComment(
                !action.equals(Action.view)
                        && !action.equals(Action.fillForms)
                        && !action.equals(Action.embedded)
                        && !action.equals(Action.blockcontent)
        );

        userPermissions.setFillForms(
                !action.equals(Action.view)
                        && !action.equals(Action.comment)
                        && !action.equals(Action.embedded)
                        && !action.equals(Action.blockcontent)
        );

        userPermissions.setReview(canEdit
                && (action.equals(Action.review) || action.equals(Action.edit)));

        userPermissions.setEdit(canEdit
                && (action.equals(Action.view)
                || action.equals(Action.edit)
                || action.equals(Action.filter)
                || action.equals(Action.blockcontent)));

        return userPermissions;
    }
}
