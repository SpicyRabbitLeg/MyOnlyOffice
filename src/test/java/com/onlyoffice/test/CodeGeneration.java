package com.onlyoffice.test;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

/**
 * 代码生成工具，基于Mybatis-Plus
 */
public class CodeGeneration {



    @Test
    public void doCode() {
        // 创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = "C:\\Users\\12709\\Desktop\\onlyoffice";
        gc.setOutputDir(projectPath + "\\src\\main\\java");
        gc.setAuthor("SpicyRabbitLeg");
        gc.setOpen(false);
        // 重新生成时文件是否覆盖
        gc.setFileOverride(false);
        // 去掉Service的首字母I
        gc.setServiceName("%sService");
        // POJO主键的生成策略
        gc.setIdType(IdType.ID_WORKER_STR);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(false); // swagger，如果需要的话可以开启
        mpg.setGlobalConfig(gc);

        // 数据库设置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.109.128:3306/hgb?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&useSSL=false");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setDbType(DbType.MYSQL); // 数据库类型
        mpg.setDataSource(dsc);


        // 包路径配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com"); // 统一前缀
        pc.setModuleName("onlyoffice"); // 模块名称
        pc.setController("controller");
        pc.setEntity("model");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();

        /** 【需要生成的表的名称】*/
        strategy.setInclude("only_office_file");
        // 开启驼峰命名法
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 使用Lombok技术
        strategy.setEntityLombokModel(true);
        // Restful API
        strategy.setRestControllerStyle(true);
        // url中驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setVersionFieldName("version"); // 锁
        strategy.setLogicDeleteFieldName("status"); // 状态
        strategy.setSkipView(true);
        mpg.setStrategy(strategy);


        // 执行生成代码
        mpg.execute();
    }
}
