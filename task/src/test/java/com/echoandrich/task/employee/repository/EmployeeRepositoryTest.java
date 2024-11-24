package com.echoandrich.task.employee.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @DisplayName("[특정 사원 조회 : 사원이 존재하는 경우]")
    @Test
    public void findById() {

        //given
        Integer employeeId = 100;

        //when
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        //then
        assertThat(employee).isPresent();
    }

    @DisplayName("[특정 사원 조회 : 사원이 존재하지 않는 경우]")
    @Test
    public void findByIdWithInvalidId() {

        //given
        Integer employeeId = 0;

        //when
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        //then
        assertThat(employee).isNotPresent();
    }

    @DisplayName("[부서별 사원 조회 : 사원들이 존재할 경우]")
    @Test
    public void findByDepartmentId() {

        //given
        Integer departmentId = 50;
        PageRequest paging = PageRequest.of(0, 20);

        //when
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId, paging);

        //then
        assertThat(employees.size()).isLessThanOrEqualTo(20);
    }
}