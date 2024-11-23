package com.echoandrich.task.common.validation.decimal;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DecimalValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDecimal {
    String message() default "Invalid decimal format.";
    int precision() default 10;     // 총 자릿수
    int scale() default 2;          // 소수점 이하 자릿수
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}