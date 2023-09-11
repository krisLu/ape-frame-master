package com.jingdianjichi.user.controller;

import java.util.Date;

import com.jingdianjichi.redis.util.RedisShareLockUtil;
import com.jingdianjichi.redis.util.RedisUtil;
import com.jingdianjichi.tool.ExportWordUtil;
import com.jingdianjichi.tool.checker.Checker;
import com.jingdianjichi.tool.checker.Checkers;
import com.jingdianjichi.user.entity.po.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class TestController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisShareLockUtil redisShareLockUtil;

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/testRedis")
    public void testRedis() {
        redisUtil.set("name", "鸡翅");
    }

    @GetMapping("/testRedisLock")
    public void testRedisLock() {
        boolean result = redisShareLockUtil.lock("jichi", "1231231", 100000L);
        System.out.println(result);
    }

    @GetMapping("/testLog")
    public void testLog() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            log.info("这是{}条日志！", i);
        }
        long endTime = System.currentTimeMillis();
        log.info("当前耗时：{}", endTime - startTime);
    }

    @GetMapping("/testExport")
    public void testExport() throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", "经典鸡翅");
        dataMap.put("auditName", "可乐鸡翅");
        ExportWordUtil.exportWord(dataMap, "导出文件", "wordExport.ftl");
    }

    @PostMapping("/testQuery")
    public void testQuery(@RequestBody SysUser sysUser) throws Exception {
        //2022-12-18 21:49:00
        System.out.println(sysUser);
    }


    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            SysUser sysUser = new SysUser();
            sysUser.setId(0L);
            sysUser.setName("");
            sysUser.setAge(-10);
            sysUser.setCreateBy("");
            sysUser.setCreateTime(new Date());
            sysUser.setUpdateBy("");
            sysUser.setUpdateTime(new Date());
            sysUser.setDeleteFlag(0);
            sysUser.setVersion(0);

            Checker<SysUser> checker = Checkers.<SysUser>lambdaCheck()
                    .notNull(SysUser::getName)
                    .ne(SysUser::getAge, 0)
                    .custom(item -> item.getAge() > queryByDb(item.getId()), "年龄异常");
            checker.check(sysUser);
            System.out.println("cost " + (System.currentTimeMillis() - start));
        }

    }

    /**
     * 模拟通过db获取判断值进行自定义判断
     */
    private static Integer queryByDb(Long id) {
        if (id > 0) {
            return -1;
        } else {
            return 1;
        }
    }


}
