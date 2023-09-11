package com.jingdianjichi.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ChickenWing
 * @Description: 线程池配置示例
 * @DateTime: 2023/1/8 21:49
 */
@Configuration
public class ThreadPollConfig {

    @Bean(name = "mailThreadPool")
    public ThreadPoolExecutor getMailThreadPool() {
        ThreadFactory threadFactory = new CustomNameThreadFactory("mail");
        return new ThreadPoolExecutor(20,
                240,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue(40),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
