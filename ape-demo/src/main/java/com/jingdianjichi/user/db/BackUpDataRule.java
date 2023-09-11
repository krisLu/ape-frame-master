package com.jingdianjichi.user.db;

import lombok.Data;

/**
 * 数据归档实体
 *
 * @author: ChickenWing
 * @date: 2023/3/26
 */
@Data
public class BackUpDataRule {

    /**
     * 开始归档的id
     */
    private Long beginId;

    /**
     * 结束归档的id
     */
    private Long endId;

    /**
     * 一次查询的条数
     */
    private Long querySize;

}