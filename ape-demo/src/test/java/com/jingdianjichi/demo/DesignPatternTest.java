package com.jingdianjichi.demo;

import com.alibaba.fastjson.JSON;
import com.jingdianjichi.redis.util.RedisShareLockUtil;
import com.jingdianjichi.user.DemoApplication;
import com.jingdianjichi.user.delayQueue.MassMailTask;
import com.jingdianjichi.user.delayQueue.MassMailTaskService;
import com.jingdianjichi.user.designPattern.responsibilityChainPattern.ArticleInfo;
import com.jingdianjichi.user.designPattern.responsibilityPattern.base.RuleCheckProcessorChain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class DesignPatternTest {

    @Resource
    private RuleCheckProcessorChain ruleCheckProcessorChain;

    @Test
    public void push() {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setTitle("zhengtong");
        articleInfo.setContent("zhengtong");
        articleInfo.setAuthor("zhengtong");
        ruleCheckProcessorChain.processInfo(articleInfo);
        List<String> ruleResult = ruleCheckProcessorChain.getRuleResult();
        System.out.println("ruleResult = " + ruleResult);
    }



}
