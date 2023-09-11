package com.jingdianjichi.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * demo项目启动类
 *
 * @author: ChickenWing
 * @date: 2023/1/26
 */
@SpringBootApplication
@MapperScan(value = "com.jingdianjichi.*.dao")
@ComponentScan(value = "com.jingdianjichi")
@EnableCaching
public class DemoApplication {

    public static void main(String[] args) {
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        SpringApplication.run(DemoApplication.class);
    }

}
