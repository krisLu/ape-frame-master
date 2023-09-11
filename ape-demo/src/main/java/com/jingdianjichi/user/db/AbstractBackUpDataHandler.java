package com.jingdianjichi.user.db;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 抽象数据归档接口
 *
 * @author: ChickenWing
 * @date: 2023/3/26
 */
@Slf4j
@Component
public abstract class AbstractBackUpDataHandler<T, V extends BackUpDataRule> implements BackUpDataHandler {

    /**
     * 最大循环次数，后续可以配合nacos动态配置
     */
    private Integer maxLoopCount = 10;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public void backUpData() {
        log.info("AbstractBackUpDataHandler.backUpData.start.scene:{}!", getScene().code);
        V rule = getRule();
        Integer loopCount = 0;
        log.info("AbstractBackUpDataHandler.backUpData.getRule.scene:{},rule:{}", getScene().code, JSON.toJSONString(rule));
        while (!needStop()) {
            log.info("AbstractBackUpDataHandler.backUpData.stopFlag.scene:{},stopFlag:{}", getScene().code, needStop());
            List<T> dataList = queryData(rule);
            if (CollectionUtils.isEmpty(dataList)) {
                log.info("AbstractBackUpDataHandler.backUpData.queryIsEmpty.scene:{}", getScene().code);
                if (loopCount >= maxLoopCount) {
                    log.info("AbstractBackUpDataHandler.backUpData.arriveMaxCount.scene:{}", getScene().code);
                    break;
                }
                rule = changeOffSet(rule);
                loopCount++;
                continue;
            }
            loopCount = 0;
            log.info("AbstractBackUpDataHandler.backUpData.querySize.scene:{},size:{}", getScene().code, dataList.size());
            AtomicReference<Boolean> transactionSuccessFlag = new AtomicReference<>(true);
            transactionTemplate.execute((transactionStatus) -> {
                try {
                    insertData(dataList);
                    deleteData(dataList);
                } catch (Exception e) {
                    log.error("AbstractBackUpDataHandler.backUpData.transferData.fail.scene:{},error:{}", getScene().code, e.getMessage(), e);
                    transactionSuccessFlag.set(false);
                    transactionStatus.setRollbackOnly();
                }
                return null;
            });
            if (!transactionSuccessFlag.get()) {
                log.info("AbstractBackUpDataHandler.backUpData.transactionFail.scene:{}", getScene().code);
                break;
            }
            rule = changeOffSet(rule);
        }
    }

    /**
     * 是否停止
     */
    public abstract Boolean needStop();

    /**
     * 归档规则
     */
    public abstract V getRule();

    /**
     * 归档偏移量变更
     */
    public abstract V changeOffSet(V backupDataRule);

    /**
     * 查询数据
     */
    public abstract List<T> queryData(V backUpDataRule);

    /**
     * 插入数据
     */
    public abstract void insertData(List<T> dataList);

    /**
     * 删除数据
     */
    public abstract void deleteData(List<T> dataList);

    /**
     * 场景定义
     */
    @Override
    public abstract BackUpDataSceneEnum getScene();

}