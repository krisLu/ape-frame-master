package com.jingdianjichi.home;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * 首页项目启动类
 *
 * @author: ChickenWing
 * @date: 2023/1/26
 */
@SpringBootApplication
@MapperScan(value = "com.jingdianjichi.*.dao")
@ComponentScan(value = "com.jingdianjichi")
@EnableCaching
//@RibbonClient(name = "ape-cloud-sku",configuration = ChickenRuleConfig.class)
public class HomeApplication {

    public static void main(String[] args) {
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        SpringApplication.run(HomeApplication.class);
    }

}
