package com.echoandrich.task.common.validation.decimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class DecimalValidator implements ConstraintValidator<ValidDecimal, BigDecimal> {

    private int precision;
    private int scale;

    @Override
    public void initialize(ValidDecimal constraintAnnotation) {
        this.precision = constraintAnnotation.precision();
        this.scale = constraintAnnotation.scale();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String[] parts = value.stripTrailingZeros().toPlainString().split("\\.");
        int integerLength = parts[0].length();
        int fractionLength = parts.length > 1 ? parts[1].length() : 0;

        // 정수부와 소수부를 합친 자릿수 검증
        return (integerLength + fractionLength) <= precision && fractionLength <= scale;
    }
}