package com.jingdianjichi.tool.validator;

import lombok.Data;

import java.util.Map;

@Data
public class ParamException extends RuntimeException {

    /**
     * 错误码
     */
    protected int code;

    /**
     * 错误码str
     */
    protected String codeDesc;

    /**
     * 额外信息
     */
    protected Map<String, Object> extra = null;

    public ParamException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ParamException(Integer code, String message, String codeDesc) {
        this(code, message);
        this.codeDesc = codeDesc;
    }

    public ParamException(Integer code, String message, String codeDesc, Throwable throwable) {
        this(code, message, throwable);
        this.codeDesc = codeDesc;
    }

    public ParamException(Integer code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }
}