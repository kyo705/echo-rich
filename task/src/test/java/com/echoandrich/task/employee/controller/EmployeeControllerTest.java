package com.echoandrich.task.employee.controller;

import com.echoandrich.task.employee.SetupEmployee;
import com.echoandrich.task.employee.constants.EmployeeConstants;
import com.echoandrich.task.employee.dto.EmployeeDto;
import com.echoandrich.task.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.echoandrich.task.department.constants.DepartmentConstants.NOT_EXISTING_DEPARTMENT_MESSAGE;
import static com.echoandrich.task.employee.constants.EmployeeConstants.NOT_EXISTING_EMPLOYEE_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        EmployeeDto employee = EmployeeDto.create(SetupEmployee.employee(employeeId));

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

    @DisplayName("[특정 사원 정보 업데이트 : 사원이 존재하지 않는 경우]")
    @Test
    void findByIdWithNotExistingEmployee() throws Exception {

        //given
        Integer employeeId = 100;
        String requestBody = """
                {
                    "firstName" : "sunkyo"
                }
                """;

        BDDMockito.given(employeeService.update(any(), any()))
                .willThrow(new IllegalArgumentException(NOT_EXISTING_EMPLOYEE_MESSAGE));

        //when & then
        mvc.perform(patch(EmployeeConstants.EMPLOYEE_PATH_URI, employeeId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println(responseBody);

                    assertThat(responseBody).isEqualTo(NOT_EXISTING_EMPLOYEE_MESSAGE);
                })
        ;
    }

    @DisplayName("[특정 사원 정보 업데이트 : 조건에 맞지 않는 requestBody가 들어온 경우]")
    @MethodSource("com.echoandrich.task.employee.SetupEmployee#invalidUpdatingConditions")
    @ParameterizedTest
    void findByIdWithInvalidRequestBody(String requestBody) throws Exception {

        //given
        Integer employeeId = 100;

        BDDMockito.given(employeeService.update(any(), any()))
                .willReturn(EmployeeDto.create(SetupEmployee.employee(employeeId)));

        //when & then
        mvc.perform(patch(EmployeeConstants.EMPLOYEE_PATH_URI, employeeId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println(responseBody);
                })
        ;
    }

    @DisplayName("[부서 전체 급여 인상 : 부서가 존재하지 않는 경우]")
    @Test
    void increaseSalaryWithDepartmentGroupWithNotExistingDepartment() throws Exception {

        //given
        Integer departmentId = 100;
        String requestBody = """
                {
                    "salaryIncreaseRate" : "0.1"
                }
                """;

        willThrow(new IllegalArgumentException(NOT_EXISTING_DEPARTMENT_MESSAGE))
                .given(employeeService)
                .increaseSalaryWithDepartmentGroup(any(), any());

        //when & then
        mvc.perform(post(EmployeeConstants.DEPARTMENT_SALARY_INCREASE_PATH_URI, departmentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println(responseBody);

                    assertThat(responseBody).isEqualTo(NOT_EXISTING_DEPARTMENT_MESSAGE);
                })
        ;
    }

    @DisplayName("[부서 전체 급여 인상 : 부서가 존재하지 않는 경우]")
    @Test
    void increaseSalaryWithDepartmentGroupWithInvalidIncreaseSalaryRate() throws Exception {

        //given
        Integer departmentId = 100;
        String requestBody = """
                {
                    "salaryIncreaseRate" : "1.1"
                }
                """;

        //when & then
        mvc.perform(post(EmployeeConstants.DEPARTMENT_SALARY_INCREASE_PATH_URI, departmentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    System.out.println(responseBody);
                })
        ;
    }
}