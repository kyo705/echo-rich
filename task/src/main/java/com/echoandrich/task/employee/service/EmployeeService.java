package com.echoandrich.task.employee.service;

import com.echoandrich.task.employee.dto.EmployeeDto;
import com.echoandrich.task.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public EmployeeDto findById(Long employeeId) {

        return employeeRepository.findById(employeeId)
                .map(EmployeeDto::create)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 employee id 입니다."));
    }
}
