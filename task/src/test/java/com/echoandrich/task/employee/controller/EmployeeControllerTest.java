package com.echoandrich.task.employee.controller;

import com.echoandrich.task.employee.constants.EmployeeConstants;
import com.echoandrich.task.employee.dto.EmployeeDto;
import com.echoandrich.task.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.echoandrich.task.employee.constants.EmployeeConstants.NOT_EXISTING_EMPLOYEE_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @DisplayName("[특정 사원 조회 : 사원이 존재하는 경우]")
    @Test
    void findById() throws Exception {

        //given
        Integer employeeId = 100;

        EmployeeDto employee = new EmployeeDto();
        employee.setEmployeeId(employeeId);
        employee.setFirstName("Steven");
        employee.setLastName("King");
        employee.setEmail("SKING");
        employee.setPhoneNumber("515.123.4567");
        employee.setHireDate(LocalDate.parse("1987-06-17"));
        employee.setJobId("AD_PRES");
        employee.setSalary(new BigDecimal("24000.00"));
        employee.setCommissionPct(new BigDecimal("0.20"));
        employee.setManagerId(null);
        employee.setDepartmentId(90);

        BDDMockito.given(employeeService.findById(employeeId)).willReturn(employee);

        //when & then
        mvc.perform(get(EmployeeConstants.EMPLOYEE_PATH_URI, employeeId))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println(responseBody);

                    EmployeeDto responseEmployee = objectMapper.readValue(responseBody, EmployeeDto.class);
                    assertThat(responseEmployee.getEmployeeId()).isEqualTo(employee.getEmployeeId());
                    assertThat(responseEmployee.getFirstName()).isEqualTo(employee.getFirstName());
                    assertThat(responseEmployee.getLastName()).isEqualTo(employee.getLastName());
                    assertThat(responseEmployee.getEmail()).isEqualTo(employee.getEmail());
                    assertThat(responseEmployee.getPhoneNumber()).isEqualTo(employee.getPhoneNumber());
                    assertThat(responseEmployee.getHireDate()).isEqualTo(employee.getHireDate());
                    assertThat(responseEmployee.getJobId()).isEqualTo(employee.getJobId());
                    assertThat(responseEmployee.getSalary()).isEqualTo(employee.getSalary());
                    assertThat(responseEmployee.getCommissionPct()).isEqualTo(employee.getCommissionPct());
                    assertThat(responseEmployee.getManagerId()).isEqualTo(employee.getManagerId());
                    assertThat(responseEmployee.getDepartmentId()).isEqualTo(employee.getDepartmentId());
                })
        ;
    }

    @DisplayName("[특정 사원 조회 : 사원이 존재하지 않는 경우]")
    @Test
    void findByIdWithInvalidId() throws Exception {

        //given
        Integer employeeId = 100;

        BDDMockito.given(employeeService.findById(employeeId))
                .willThrow(new IllegalArgumentException(NOT_EXISTING_EMPLOYEE_MESSAGE));

        //when & then
        mvc.perform(get(EmployeeConstants.EMPLOYEE_PATH_URI, employeeId))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println(responseBody);

                    assertThat(responseBody).isEqualTo(NOT_EXISTING_EMPLOYEE_MESSAGE);
                })
        ;
    }
}