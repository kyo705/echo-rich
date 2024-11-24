package com.echoandrich.task.employee.controller;

import com.echoandrich.task.employee.dto.EmployeeDto;
import com.echoandrich.task.employee.dto.EmployeeUpdatingDto;
import com.echoandrich.task.employee.dto.SalaryIncreasingDto;
import com.echoandrich.task.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.echoandrich.task.employee.constants.EmployeeConstants.DEPARTMENT_SALARY_INCREASE_PATH_URI;
import static com.echoandrich.task.employee.constants.EmployeeConstants.EMPLOYEE_PATH_URI;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping(EMPLOYEE_PATH_URI)
    public ResponseEntity<EmployeeDto> findById(@PathVariable Integer employeeId) {

        EmployeeDto responseBody = employeeService.findById(employeeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @PatchMapping(EMPLOYEE_PATH_URI)
    public ResponseEntity<EmployeeDto> update(@PathVariable Integer employeeId,
                                              @Valid @RequestBody EmployeeUpdatingDto requestBody) {

        EmployeeDto responseBody = employeeService.update(employeeId, requestBody);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @PostMapping(DEPARTMENT_SALARY_INCREASE_PATH_URI)
    public ResponseEntity<Void> increaseSalaryWithDepartmentGroup(
            @PathVariable Integer departmentId,
            @RequestBody @Valid SalaryIncreasingDto requestBody) {

        employeeService.increaseSalaryWithDepartmentGroup(departmentId, requestBody.getSalaryIncreaseRate());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
