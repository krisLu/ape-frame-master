package com.jingdianjichi.tool.validator;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ValidatorUtil {

    private static final Integer PARAM_ERROR_CODE = 1000;

    private static final String PARAM_ERROR_MSG = "参数错误";

    public static final Validator VALIDATOR = Validation
            .byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();

    public static <T> void validate(T obj, Class<?>... groups) throws ParamException {
        Set<ConstraintViolation<T>> constraintViolations;
        if (groups == null) {
            constraintViolations = VALIDATOR.validate(obj);
        } else {
            constraintViolations = VALIDATOR.validate(obj, groups);
        }
        if (constraintViolations.size() > 0) {
            HashMap<String, Object> extMap = new HashMap<>();
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> constraintViolation = iterator.next();
                extMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            ParamException paramException = new ParamException(PARAM_ERROR_CODE, PARAM_ERROR_MSG + "[" + extMap.toString() + "]");
            paramException.setExtra(extMap);
            throw paramException;
        }
    }

}
