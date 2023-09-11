package com.jingdianjichi.demo;

import com.jingdianjichi.tool.CompletableFutureUtils;
import com.jingdianjichi.user.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ChickenWing
 * @Description: 自定义线程池测试
 * @DateTime: 2023/1/8 22:16
 */
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class MailThreadPoolTest {

    @Resource(name = "mailThreadPool")
    private ThreadPoolExecutor mailThreadPool;

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            mailThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    log.info("当前时间:" + System.currentTimeMillis());
                }
            });
        }
    }

    @Test
    public void testFuture() {
        List<FutureTask<String>> futureTaskList = new LinkedList<>();
        FutureTask futureTask1 = new FutureTask<String>(() -> {
            return "鸡翅";
        });
        FutureTask futureTask2 = new FutureTask<String>(() -> {
            Thread.sleep(2000);
            return "经典";
        });
        futureTaskList.add(futureTask1);
        futureTaskList.add(futureTask2);
        mailThreadPool.submit(futureTask1);
        mailThreadPool.submit(futureTask2);

        for (int i = 0; i < futureTaskList.size(); i++) {
            String name = CompletableFutureUtils.getResult(futureTaskList.get(i),
                    1, TimeUnit.SECONDS, "经典鸡翅", log);
            log.info("MailThreadPoolTest.name:{}",name);
        }

    }

}
