package com.jingdianjichi.user.event;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @Author: ChickenWing
 * @Description: 事件listener
 * @DateTime: 2023/1/8 23:02
 */
@Service
@Slf4j
public class PersonEventListener {

    @TransactionalEventListener(fallbackExecution = true)
    public void listenSecKillCreateEvent(PersonChangeEvent event) {
        switch (event.getOperateType()) {
            case "create":
                log.info("执行创建相关事件,person：{}", JSON.toJSONString(event.getPerson()));
                break;
            default:
                break;
        }
    }

}
