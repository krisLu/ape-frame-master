package com.jingdianjichi.tool.checker.fun;

import java.io.Serializable;

/**
 * 支持序列化的 Function
 * 为了获取字段名字
 *
 * @author loser
 * @date 2023/06/12
 */
@FunctionalInterface
public interface SFunction<T, R> extends Serializable {

    /**
     * 执行方法
     */
    R apply(T t);

}
