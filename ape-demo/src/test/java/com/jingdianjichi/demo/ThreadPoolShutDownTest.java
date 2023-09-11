package com.jingdianjichi.demo;

import com.jingdianjichi.user.DemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池关闭测试类
 *
 * @author: ChickenWing
 * @date: 2023/1/20
 */
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadPoolShutDownTest {

    @Test
    public void testShutDown() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new TaskShutDownPool());
        }
        Thread.sleep(1000);
        log.info("ThreadPoolShutDownTest.testShutDown.status:{}", executorService.isShutdown() + ",调用 shutdown() 方法之前");
        executorService.shutdown();
        log.info("ThreadPoolShutDownTest.testShutDown.status:{}", executorService.isShutdown() + ",调用 shutdown() 方法之后");
        Thread.sleep(500);
        log.info("ThreadPoolShutDownTest.testShutDown");
        executorService.execute(new TaskShutDownPool());
    }

    @Test
    public void testTerminated() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new TaskShutDownPool());
        }
        Thread.sleep(1000);
        log.info("ThreadPoolShutDownTest.testTerminated.status:{}", executorService.isTerminated() + ",调用 shutdown() 方法之前");
        executorService.shutdown();
        log.info("ThreadPoolShutDownTest.testTerminated.status:{}", executorService.isTerminated() + ",调用 shutdown() 方法之后");
        Thread.sleep(500);
        log.info("ThreadPoolShutDownTest.testShutDown");
        executorService.execute(new TaskShutDownPool());
    }

    @Test
    public void testAwaitTermination() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new TaskShutDownPool());
        }
        Thread.sleep(1000);
        executorService.shutdown();
        log.info("ThreadPoolShutDownTest.testAwaitTermination.status:{}", executorService.awaitTermination(10L, TimeUnit.SECONDS));
        Thread.sleep(500);
        log.info("ThreadPoolShutDownTest.testShutDown");
        executorService.execute(new TaskShutDownPool());
    }

    @Test
    public void testShutDownNow() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new TaskShutDownPool());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Runnable> runnableList = executorService.shutdownNow();
        for (Runnable runnableList1 : runnableList) {
            log.info("ThreadPoolShutDownTest.testShutDownNow.runnable:{}", runnableList1);
        }
    }

    class TaskShutDownPool implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(500);
                log.info(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                log.info("TaskShutDownPool.interrupted:{}", e.getMessage(), e);
            }
        }
    }

}
