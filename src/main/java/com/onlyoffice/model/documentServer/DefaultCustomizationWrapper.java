package com.onlyoffice.model.documentServer;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefaultCustomizationWrapper{
        private Action action;
        private EditorConfig.User user;
}
