package com.onlyoffice.test;

import com.onlyoffice.model.documentServer.EditorConfig;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

public class MainTest {

    @Test
    public void testConfig() throws Exception{
        FileInputStream inputStream = new FileInputStream("C:\\Users\\12709\\Desktop\\index.jpg");
        MultipartFile multipartFile = new MockMultipartFile("index.jpg", "index.jpg", MediaType.IMAGE_JPEG_VALUE, IOUtils.toByteArray(inputStream));
    }


    @Test
    public void testJWT() throws Exception{
        System.out.println(EditorConfig.Mode.edit.name());
    }
}
