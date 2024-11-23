package com.echoandrich.task.employee.service;

import com.echoandrich.task.employee.dto.EmployeeDto;
import com.echoandrich.task.employee.repository.Employee;
import com.echoandrich.task.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @DisplayName("[특정 사원 조회 : 사원이 존재하는 경우]")
    @Test
    void findById() {

        //given
        Integer employeeId = 100;

        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setFirstName("Steven");
        employee.setLastName("King");
        employee.setEmail("SKING");
        employee.setPhoneNumber("515.123.4567");
        employee.setHireDate(LocalDate.parse("1987-06-17"));
        employee.setJobId("AD_PRES");
        employee.setSalary(new BigDecimal("24000.00"));
        employee.setCommissionPct(null);
        employee.setManagerId(null);
        employee.setDepartmentId(90);

        BDDMockito.given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));

        //when
        EmployeeDto employeeDto = employeeService.findById(employeeId);

        //then
        assertThat(employeeDto.getEmployeeId()).isEqualTo(employee.getEmployeeId());
        assertThat(employeeDto.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(employeeDto.getLastName()).isEqualTo(employee.getLastName());
        assertThat(employeeDto.getEmail()).isEqualTo(employee.getEmail());
        assertThat(employeeDto.getPhoneNumber()).isEqualTo(employee.getPhoneNumber());
        assertThat(employeeDto.getHireDate()).isEqualTo(employee.getHireDate());
        assertThat(employeeDto.getJobId()).isEqualTo(employee.getJobId());
        assertThat(employeeDto.getSalary()).isEqualTo(employee.getSalary());
        assertThat(employeeDto.getCommissionPct()).isEqualTo(employee.getCommissionPct());
        assertThat(employeeDto.getManagerId()).isEqualTo(employee.getManagerId());
        assertThat(employeeDto.getDepartmentId()).isEqualTo(employee.getDepartmentId());
    }

    @DisplayName("[특정 사원 조회 : 사원이 존재하지 않는 경우]")
    @Test
    void findByIdWithInvalidId() {

        //given
        Integer employeeId = 100;

        BDDMockito.given(employeeRepository.findById(employeeId)).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() ->employeeService.findById(employeeId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}