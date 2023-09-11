package com.jingdianjichi.tool.checker;

import com.jingdianjichi.tool.checker.fun.HandleFunction;
import com.jingdianjichi.tool.checker.fun.Predicate;
import com.jingdianjichi.tool.checker.fun.SFunction;
import com.jingdianjichi.tool.checker.handler.ConditionHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 参数校验工具
 *
 * @author alan
 * @date 2023/06/12
 */
public class Checker<T> {

    private final List<Condition<T>> conditions = new ArrayList<>();

    private static final Map<ECheckType, HandleFunction> FUNCTION_HASH_MAP = new HashMap<>();

    static {
        FUNCTION_HASH_MAP.put(ECheckType.NE, ConditionHandler::handleNe);
        FUNCTION_HASH_MAP.put(ECheckType.NOTNULL, ConditionHandler::handleNotNull);
        FUNCTION_HASH_MAP.put(ECheckType.NOTNULL_NE, ConditionHandler::handleNotNullNe);
        FUNCTION_HASH_MAP.put(ECheckType.IN, ConditionHandler::handleIn);
        FUNCTION_HASH_MAP.put(ECheckType.CUSTOM, ConditionHandler::handleCustom);
    }

    /**
     * 判断该值不能为传入值
     */
    public Checker<T> ne(SFunction<T, ?> column, Object val, String desc) {
        conditions.add(new Condition<>(getFieldMeta(column), ECheckType.NE, val, desc));
        return this;
    }

    /**
     * 判断值不能为空
     */
    public Checker<T> notNull(SFunction<T, ?> column, String desc) {
        conditions.add(new Condition<T>(getFieldMeta(column), ECheckType.NOTNULL, null, desc));
        return this;
    }

    /**
     * 不能为空且不能为传入值
     */
    public Checker<T> notNe(SFunction<T, ?> column, Object val, String desc) {
        conditions.add(new Condition<>(getFieldMeta(column), ECheckType.NOTNULL_NE, val, desc));
        return this;
    }

    /**
     * 不能为空且不能为传入值
     */
    public Checker<T> in(SFunction<T, ?> column, Collection<Object> val, String desc) {
        conditions.add(new Condition<>(getFieldMeta(column), ECheckType.IN, val, desc));
        return this;
    }

    /**
     * 判断该值不能为传入值
     */
    public Checker<T> ne(SFunction<T, ?> column, Object val) {
        return ne(column, val, null);
    }

    /**
     * 判断值不能为空
     */
    public Checker<T> notNull(SFunction<T, ?> column) {
        return notNull(column, null);
    }

    /**
     * 不能为空且不能为传入值
     */
    public Checker<T> notNe(SFunction<T, ?> column, Object val) {
        return notNe(column, val, null);
    }

    /**
     * 不能为空且不能为传入值
     */
    public Checker<T> in(SFunction<T, ?> column, Collection<Object> val) {
        return in(column, val, null);
    }

    /**
     * 自定义规则 条件成立则抛出 业务异常
     */
    public Checker<T> custom(Predicate<T> resultPredicate, String desc) {
        Condition<T> condition = new Condition<>("", ECheckType.CUSTOM, null, desc);
        condition.setResultPredicate(resultPredicate);
        conditions.add(condition);
        return this;
    }

    /**
     * 执行校验逻辑
     */
    public void check(T obj) {

        if (Objects.isNull(obj)) {
            throw new CheckerException("参数为空");
        }
        for (Condition<T> condition : conditions) {
            FUNCTION_HASH_MAP.get(condition.getType()).apply(obj, condition);
        }

    }

    private String getFieldMeta(SFunction<T, ?> column) {
        return ConvertUtil.convertToFieldName(column);
    }

}
