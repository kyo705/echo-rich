package com.echoandrich.task.employee.service;

import com.echoandrich.task.department.Department;
import com.echoandrich.task.department.DepartmentRepository;
import com.echoandrich.task.employee.dto.EmployeeDto;
import com.echoandrich.task.employee.dto.EmployeeUpdatingDto;
import com.echoandrich.task.employee.repository.Employee;
import com.echoandrich.task.employee.repository.EmployeeRepository;
import com.echoandrich.task.job.Job;
import com.echoandrich.task.job.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.echoandrich.task.department.DepartmentConstants.NOT_EXISTING_DEPARTMENT_MESSAGE;
import static com.echoandrich.task.employee.constants.EmployeeConstants.*;
import static com.echoandrich.task.job.JobConstants.NOT_EXISTING_JOB_MESSAGE;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final JobRepository jobRepository;

    private final DepartmentRepository departmentRepository;


    @Transactional(readOnly = true)
    public EmployeeDto findById(Integer employeeId) {

        return employeeRepository.findById(employeeId)
                .map(EmployeeDto::create)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTING_EMPLOYEE_MESSAGE));
    }

    @Transactional
    public EmployeeDto update(Integer employeeId, EmployeeUpdatingDto updatingConditions) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTING_EMPLOYEE_MESSAGE));

        if(updatingConditions.getFirstName() != null) {
            employee.setFirstName(updatingConditions.getFirstName());
        }
        if(updatingConditions.getLastName() != null) {
            employee.setLastName(updatingConditions.getLastName());
        }
        if(updatingConditions.getEmail() != null) {
            employee.setEmail(updatingConditions.getEmail());
        }
        if(updatingConditions.getPhoneNumber() != null) {
            employee.setPhoneNumber(updatingConditions.getPhoneNumber());
        }
        if(updatingConditions.getSalary() != null) {
            employee.setSalary(updatingConditions.getSalary());
        }
        if(updatingConditions.getCommissionPct() != null) {
            employee.setCommissionPct(updatingConditions.getCommissionPct());
        }
        if(updatingConditions.getJobId() != null) {
            // jobId 검증
            Job job = jobRepository.findById(updatingConditions.getJobId())
                    .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTING_JOB_MESSAGE));

            if(employee.getSalary().compareTo(job.getMinSalary()) < 0) {    // salary 가 최소보다 작은 경우
                throw new IllegalArgumentException(SALARY_TOO_SMALL_MESSAGE);
            }
            if(employee.getSalary().compareTo(job.getMaxSalary()) > 0) {    // salary 가 최소보다 작은 경우
                throw new IllegalArgumentException(SALARY_TOO_BIG_MESSAGE);
            }
            // job id 업데이트
            employee.setJobId(job.getJobId());
        }
        if(updatingConditions.getDepartmentId() != null) {
            // departmentId 검증
            Department department = departmentRepository.findById(updatingConditions.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTING_DEPARTMENT_MESSAGE));;

            // 부서 변경 및 매니저 변경
            employee.setDepartmentId(department.getDepartmentId());
            employee.setManagerId(department.getManagerId());
        }

        return EmployeeDto.create(employeeRepository.save(employee));
    }
}
