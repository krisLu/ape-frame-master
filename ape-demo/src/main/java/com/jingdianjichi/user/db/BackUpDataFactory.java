package com.jingdianjichi.user.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BackUpDataFactory implements InitializingBean {

    @Resource
    private List<BackUpDataHandler> backUpDataHandlerList;

    private Map<BackUpDataSceneEnum, BackUpDataHandler> handlerMap = new HashMap<>();

    public BackUpDataHandler getHandlerByCode(String code) {
        BackUpDataSceneEnum backUpDataSceneEnum = BackUpDataSceneEnum.getByCode(code);
        return handlerMap.get(backUpDataSceneEnum);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (BackUpDataHandler backUpDataHandler : backUpDataHandlerList) {
            handlerMap.put(backUpDataHandler.getScene(), backUpDataHandler);
        }
    }

}