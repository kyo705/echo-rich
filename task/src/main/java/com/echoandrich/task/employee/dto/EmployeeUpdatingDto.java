package com.echoandrich.task.employee.dto;

import com.echoandrich.task.common.validation.decimal.ValidDecimal;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

import static com.echoandrich.task.common.constants.RegexConstants.EMAIL_LOCAL_PART_REGEX;
import static com.echoandrich.task.common.constants.RegexConstants.PHONE_NUMBER_REGEX;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdatingDto {

    @Size(min = 1, max = 20)
    private String firstName;

    @Size(min = 1,max = 25)
    private String lastName;

    @Size(min = 1,max = 25)
    @Pattern(regexp = EMAIL_LOCAL_PART_REGEX)
    private String email;

    @Size(min = 1, max = 20)
    @Pattern(regexp = PHONE_NUMBER_REGEX)
    private String phoneNumber;

    @Size(min = 1, max = 10)
    private String jobId;               // 직업 변경시 기존 salary 가 job의 min salary 보다 크도록 변경

    @ValidDecimal(precision = 8, scale = 2)
    private BigDecimal salary;          // 변경 가능

    @ValidDecimal(precision = 2, scale = 2)
    private BigDecimal commissionPct;   // 변경 가능

    private Integer departmentId;       // 부서가 변경될 수 있음 -> 사원의 manager id도 변경
}
