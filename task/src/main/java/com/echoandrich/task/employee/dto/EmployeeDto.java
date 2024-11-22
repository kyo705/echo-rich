package com.echoandrich.task.employee.dto;

import com.echoandrich.task.employee.repository.Employee;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class EmployeeDto {

    private Long employeeId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalDate hireDate;

    private String jobId;

    private BigDecimal salary;

    private BigDecimal commissionPct;

    private Integer managerId;

    private Integer departmentId;

    public static EmployeeDto create(Employee employee) {

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId(employee.getEmployeeId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setPhoneNumber(employee.getPhoneNumber());
        employeeDto.setHireDate(employee.getHireDate());
        employeeDto.setJobId(employee.getJobId());
        employeeDto.setSalary(employee.getSalary());
        employeeDto.setCommissionPct(employee.getCommissionPct());
        employeeDto.setManagerId(employee.getManagerId());
        employeeDto.setDepartmentId(employee.getDepartmentId());

        return employeeDto;
    }
}
