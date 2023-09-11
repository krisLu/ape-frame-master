package com.jingdianjichi.user.db;

/**
 * 数据归档service
 *
 * @author: ChickenWing
 * @date: 2023/3/26
 */
public interface BackUpService {

    /**
     * 数据归档
     */
    void backUp(BackUpDataSceneEnum sceneEnum);

}