package com.jingdianjichi.tool.checker;

/**
 * 参数校验工具
 *
 * @author alan
 * @date 2023/06/12
 */
public class Checkers {

    public static <T> Checker<T> lambdaCheck() {
        return new Checker<>();
    }

}
