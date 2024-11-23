package com.echoandrich.task.department.controller;

import com.echoandrich.task.department.SetupDepartment;
import com.echoandrich.task.department.dto.DepartmentLocationDto;
import com.echoandrich.task.department.service.DepartmentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.echoandrich.task.department.constants.DepartmentConstants.DEPARTMENTS_PATH_URI;
import static com.echoandrich.task.department.constants.DepartmentConstants.NOT_FOUND_DEPARTMENTS_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DepartmentService departmentService;


    @DisplayName("[부서 정보 및 위치 정보 조회 : 조회할 데이터가 있는 경우]")
    @Test
    void findDepartmentsWithLocation() throws Exception {

        //given
        Integer departmentId = 50;

        List<DepartmentLocationDto> departmentLocations = SetupDepartment.departmentLocations();
        given(departmentService.findDepartmentsWithLocation(departmentId))
                .willReturn(departmentLocations);

        // when & then
        mvc.perform(get(DEPARTMENTS_PATH_URI)
                        .param("lastDepartmentId", departmentId.toString()))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    List<DepartmentLocationDto> responseDepartmentLocations = objectMapper.readValue(responseBody, new TypeReference<>() {});

                    assertThat(responseDepartmentLocations.size()).isEqualTo(departmentLocations.size());
                });

    }

    @DisplayName("[부서 정보 및 위치 정보 조회 : 더이상 가져올 값이 없을 경우]")
    @Test
    void findDepartmentsWithLocationWithNotFound() throws Exception {

        //given
        Integer departmentId = 50;

        given(departmentService.findDepartmentsWithLocation(departmentId))
                        .willReturn(List.of());

        // when & then
        mvc.perform(get(DEPARTMENTS_PATH_URI)
                        .param("lastDepartmentId", departmentId.toString()))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();

                    assertThat(responseBody).isEqualTo(NOT_FOUND_DEPARTMENTS_MESSAGE);
                });

    }
}