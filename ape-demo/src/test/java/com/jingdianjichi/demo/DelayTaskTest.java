package com.jingdianjichi.demo;

import com.alibaba.fastjson.JSON;
import com.jingdianjichi.redis.util.RedisShareLockUtil;
import com.jingdianjichi.user.DemoApplication;
import com.jingdianjichi.user.delayQueue.MassMailTask;
import com.jingdianjichi.user.delayQueue.MassMailTaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.UUID;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class DelayTaskTest {

    @Resource
    private MassMailTaskService massMailTaskService;

    @Resource
    private RedisShareLockUtil redisShareLockUtil;

    @Test
    public void push() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MassMailTask massMailTask = new MassMailTask();
        massMailTask.setTaskId(1L);
        massMailTask.setStartTime(simpleDateFormat.parse("2023-01-08 23:59:00"));
        massMailTaskService.pushMassMailTaskQueue(massMailTask);
    }

    @Test
    public void deal() throws Exception {
        String lockKey = "test.delay.task";
        String requestId = UUID.randomUUID().toString();
        try {
            boolean locked = redisShareLockUtil.lock(lockKey, requestId, 5L);
            if (!locked) {
                return;
            }
            Set<Long> taskIdSet = massMailTaskService.poolMassMailTaskQueue();
            log.info("DelayTaskTest.deal.taskIdSet:{}", JSON.toJSON(taskIdSet));
            if (CollectionUtils.isEmpty(taskIdSet)) {
                return;
            }
            //执行其他的业务逻辑
        } catch (Exception e) {
            log.error("延时任务拉取执行失败", e);
        } finally {
            redisShareLockUtil.unLock(lockKey, requestId);
        }
    }

}
