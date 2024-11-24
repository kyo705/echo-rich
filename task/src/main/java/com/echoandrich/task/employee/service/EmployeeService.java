package com.echoandrich.task.employee.service;

import com.echoandrich.task.department.repository.Department;
import com.echoandrich.task.department.repository.DepartmentRepository;
import com.echoandrich.task.employee.dto.EmployeeDto;
import com.echoandrich.task.employee.dto.EmployeeUpdatingDto;
import com.echoandrich.task.employee.repository.Employee;
import com.echoandrich.task.employee.repository.EmployeeRepository;
import com.echoandrich.task.job.Job;
import com.echoandrich.task.job.JobRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.echoandrich.task.department.constants.DepartmentConstants.NOT_EXISTING_DEPARTMENT_MESSAGE;
import static com.echoandrich.task.employee.constants.EmployeeConstants.*;
import static com.echoandrich.task.job.JobConstants.NOT_EXISTING_JOB_MESSAGE;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EntityManager entityManager;

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

        if (updatingConditions.getFirstName() != null) {
            employee.setFirstName(updatingConditions.getFirstName());
        }
        if (updatingConditions.getLastName() != null) {
            employee.setLastName(updatingConditions.getLastName());
        }
        if (updatingConditions.getEmail() != null) {
            employee.setEmail(updatingConditions.getEmail());
        }
        if (updatingConditions.getPhoneNumber() != null) {
            employee.setPhoneNumber(updatingConditions.getPhoneNumber());
        }
        if (updatingConditions.getSalary() != null) {
            employee.setSalary(updatingConditions.getSalary());
        }
        if (updatingConditions.getCommissionPct() != null) {
            employee.setCommissionPct(updatingConditions.getCommissionPct());
        }
        if (updatingConditions.getJobId() != null) {
            // jobId 검증
            Job job = jobRepository.findById(updatingConditions.getJobId())
                    .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTING_JOB_MESSAGE));

            if (employee.getSalary().compareTo(job.getMinSalary()) < 0) {    // salary 가 최소보다 작은 경우
                throw new IllegalArgumentException(SALARY_TOO_SMALL_MESSAGE);
            }
            if (employee.getSalary().compareTo(job.getMaxSalary()) > 0) {    // salary 가 최소보다 작은 경우
                throw new IllegalArgumentException(SALARY_TOO_BIG_MESSAGE);
            }
            // job id 업데이트
            employee.setJob(job);
        }
        if (updatingConditions.getDepartmentId() != null) {
            // departmentId 검증
            Department department = departmentRepository.findById(updatingConditions.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTING_DEPARTMENT_MESSAGE));


            // 부서 변경 및 매니저 변경
            employee.setDepartmentId(department.getDepartmentId());
            employee.setManagerId(department.getManagerId());
        }

        return EmployeeDto.create(employeeRepository.save(employee));
    }

    @Transactional
    public void increaseSalaryWithDepartmentGroup(Integer departmentId, BigDecimal increaseRate) {

        // departmentId 검증
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTING_DEPARTMENT_MESSAGE));

        // 특정 부서 전체 급여 인상
        int pagingOffset = 0;
        while (true) {
            PageRequest paging = PageRequest.of(pagingOffset, DEFAULT_EMPLOYEE_PAGING_SIZE)
                    .withSort(Sort.by(Sort.Order.asc("employeeId")));
            List<Employee> employees = employeeRepository.findByDepartmentId(departmentId, paging);
            for (Employee employee : employees) {
                BigDecimal increasedSalary = BigDecimal.valueOf(employee.getSalary().doubleValue() * (1 + increaseRate.doubleValue()));

                if (employee.getJob().getMaxSalary().compareTo(increasedSalary) < 0) {
                    employee.setSalary(employee.getJob().getMaxSalary());
                } else {
                    employee.setSalary(increasedSalary);
                }
            }

            // 저장 및 영속성 컨텍스트 초기화
            employeeRepository.saveAll(employees);
            entityManager.flush();
            entityManager.clear();

            // 마지막 페이징인지 체크
            if(employees.size() != DEFAULT_EMPLOYEE_PAGING_SIZE) {
                break;
            }
            pagingOffset++;
        }
    }
}
