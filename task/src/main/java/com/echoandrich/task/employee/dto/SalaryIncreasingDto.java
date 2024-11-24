package com.echoandrich.task.employee.dto;

import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SalaryIncreasingDto {

    @Digits(integer = 0, fraction = 2)
    private BigDecimal salaryIncreaseRate;
}
