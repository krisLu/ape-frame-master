package com.jingdianjichi.tool.checker;


import com.jingdianjichi.tool.checker.fun.SFunction;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 转换工具
 *
 * @author loser
 * @date 2023/06/12
 */
public class ConvertUtil {

    private ConvertUtil() {
    }

    public static final String GET = "get";

    public static final String IS = "is";

    /**
     * 缓存方法应用对应的属性名称
     */
    private static final Map<Class<?>, String> CLASS_FIELD_META_MAP = new ConcurrentHashMap<>();

    /**
     * 转换方法引用为属性名
     */
    public static <T> String convertToFieldName(SFunction<T, ?> fn) {

        SerializedLambda lambda = getSerializedLambda(fn);
        String cacheData = CLASS_FIELD_META_MAP.get(fn.getClass());
        if (Objects.nonNull(cacheData)) {
            return cacheData;
        }
        String methodName = lambda.getImplMethodName();
        if (methodName.startsWith(GET)) {
            methodName = methodName.substring(3);
        } else if (methodName.startsWith(IS)) {
            methodName = methodName.substring(2);
        } else {
            throw new IllegalArgumentException("无效的getter方法：" + methodName);
        }
        try {
            String fieldMeta = firstToLowerCase(methodName);
            CLASS_FIELD_META_MAP.put(fn.getClass(), fieldMeta);
            return fieldMeta;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获取一个实现了序列化的lambda函数
     *
     * @param fn 目标函数
     * @return 实现了序列化的lambda函数
     */
    public static SerializedLambda getSerializedLambda(Serializable fn) {

        SerializedLambda lambda;
        try {
            // 提取SerializedLambda并缓存
            Method method = fn.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            lambda = (SerializedLambda) method.invoke(fn);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return lambda;

    }

    /**
     * 首字母转换小写
     *
     * @param str 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String firstToLowerCase(final String str) {
        if (null == str || str.length() == 0) {
            return "";
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}