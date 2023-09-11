package com.jingdianjichi.tool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author: ChickenWing
 * @date: 2023/1/15
 */
@Slf4j
public class ThreadPoolUtils {

    private ThreadPoolUtils() {
    }

    public static void shutdownPool(ExecutorService pool, int shutdownTimeout, int shutdownNowTimeout, TimeUnit timeUnit) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
                    log.error("ThreadPoolUtils.shutdownPool.error");
                }
            }
        } catch (InterruptedException ie) {
            log.error("ThreadPoolUtils.shutdownPool.interrupted.error:{}", ie.getMessage(), ie);
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
