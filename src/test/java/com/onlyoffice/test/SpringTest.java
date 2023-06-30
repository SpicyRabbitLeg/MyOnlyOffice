package com.onlyoffice.test;

import com.onlyoffice.utils.FileUtility;
import com.onlyoffice.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTest {

    @Autowired
    private MD5Util md5Util;
    @Autowired
    private FileUtility fileUtility;
    @Test
    public void testMD5(){
    }
}
