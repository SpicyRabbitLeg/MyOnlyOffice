package com.onlyoffice;

import com.onlyoffice.config.MyBeanNameGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.onlyoffice.mapper")
@ComponentScan(basePackages = "com.onlyoffice",nameGenerator = MyBeanNameGenerator.class)
public class OnlyOfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlyOfficeApplication.class,args);
    }
}
