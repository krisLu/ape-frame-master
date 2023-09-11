package com.jingdianjichi.tool.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Optional;

public class ListValueValidator implements ConstraintValidator<ListValue,String> {
    private String[] list;

    @Override
    public void initialize(ListValue constraintAnnotation) {
        list = constraintAnnotation.listValue();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || "".equals(value)) {
            return true;
        }
        Optional<String> any = Arrays.stream(list).filter(obj -> obj.equals(value)).findAny();
        return any.isPresent();
    }
}