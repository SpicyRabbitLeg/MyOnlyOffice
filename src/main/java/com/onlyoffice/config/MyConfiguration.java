package com.onlyoffice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ApplicationObjectSupport;


@Configuration
public class MyConfiguration extends ApplicationObjectSupport {
    private ApplicationContext applicationContext;

    @Bean
    @Description("mybatis-plus分页支持")
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    @Bean
    @Description("mybatis-plus逻辑删除")
    public ISqlInjector iSqlInjector(){
        return new LogicSqlInjector();
    }

    @Bean
    @Description("mybatis-plus乐观锁")
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

    @Override
    protected void initApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
