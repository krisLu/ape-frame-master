package com.jingdianjichi.user.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 数据归档服务类
 *
 * @author: ChickenWing
 * @date: 2023/3/26
 */
@Service
@Slf4j
public class BackUpServiceImpl implements BackUpService {

    @Resource
    private BackUpDataFactory backUpDataFactory;

    @Override
    public void backUp(BackUpDataSceneEnum sceneEnum) {
        log.info("BackUpServiceImpl.backUp.scene:{}!", sceneEnum.getCode());
        BackUpDataHandler backUpDataHandler = backUpDataFactory.getHandlerByCode(sceneEnum.getCode());
        if (Objects.isNull(backUpDataHandler)) {
            log.info("BackUpServiceImpl.noFindScene!");
            return;
        }
        backUpDataHandler.backUpData();
    }

}