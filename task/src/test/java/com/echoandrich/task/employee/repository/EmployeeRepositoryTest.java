package com.echoandrich.task.employee.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

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
}