package com.jingdianjichi.user.job;

import com.alibaba.fastjson.JSON;
import com.jingdianjichi.user.entity.po.SysUser;
import com.jingdianjichi.user.service.SysUserService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SampleXxlJob {
    private static Logger logger = LoggerFactory.getLogger(SampleXxlJob.class);

    @Resource
    private SysUserService sysUserService;

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");
        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("beat at:" + i);
            SysUser sysUser = sysUserService.queryById(1L);
            XxlJobHelper.log("sysUser" + JSON.toJSONString(sysUser));
        }
    }

}
