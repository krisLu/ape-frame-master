package com.jingdianjichi.tool.checker.fun;

import com.jingdianjichi.tool.checker.Condition;

import java.io.Serializable;

/***
 * 功能处理器
 *
 * @author loser
 * @date 2023/06/12
 */
@FunctionalInterface
public interface HandleFunction extends Serializable {

    /**
     * 处理时间
     *
     * @param target    目标对象
     * @param condition 条件
     * @return 是否通过
     */
    boolean apply(Object target, Condition<?> condition);

}
