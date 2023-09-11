package com.jingdianjichi.tool.checker.fun;

/***
 * 断言
 *
 * @author loser
 * @date 2023/06/12
 */
@FunctionalInterface
public interface Predicate<T> {

    /**
     * 断言操作
     *
     * @param target 判断对象
     * @return 是否成
     */
    boolean apply(T target);

}
