package com.jingdianjichi.tool.checker;

/***
 * 参数校验异常
 *
 * @author loser
 * @date 2023/06/12
 */
public class CheckerException extends RuntimeException {

    public CheckerException(String message) {
        super(message);
    }

}
