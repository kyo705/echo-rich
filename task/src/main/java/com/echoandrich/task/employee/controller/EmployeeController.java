package com.echoandrich.task.employee.controller;

import com.echoandrich.task.employee.dto.EmployeeDto;
import com.echoandrich.task.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.echoandrich.task.employee.constants.EmployeeConstants.EMPLOYEE_PATH_URI;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping(EMPLOYEE_PATH_URI)
    public ResponseEntity<EmployeeDto> findById(@PathVariable Integer employeeId) {

        EmployeeDto responseBody = employeeService.findById(employeeId);

        return ResponseEntity
                .ok(responseBody);
    }

}
