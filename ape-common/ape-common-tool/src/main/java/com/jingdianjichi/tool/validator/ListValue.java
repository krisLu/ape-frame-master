package com.jingdianjichi.tool.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {ListValueValidator.class})
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValue {

    String message();

    String[] listValue();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}