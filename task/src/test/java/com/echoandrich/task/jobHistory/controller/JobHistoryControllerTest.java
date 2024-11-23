package com.echoandrich.task.jobHistory.controller;

import com.echoandrich.task.jobHistory.dto.JobHistoryDto;
import com.echoandrich.task.jobHistory.service.JobHistoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.echoandrich.task.employee.constants.EmployeeConstants.NOT_EXISTING_EMPLOYEE_MESSAGE;
import static com.echoandrich.task.jobHistory.constants.JobHistoryConstants.JOB_HISTORIES_PATH_URI;
import static com.echoandrich.task.jobHistory.constants.JobHistoryConstants.NOT_FOUND_JOB_HISTORIES_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobHistoryController.class)
class JobHistoryControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    @MockBean
    private JobHistoryService jobHistoryService;

    @DisplayName("[특정 사원의 이력 조회 : 사원이 존재하는 경우]")
    @Test
    void findJobHistories() throws Exception {

        //given
        Integer employeeId = 100;
        String startDate = "2024-11-23";

        JobHistoryDto jobHistory1 = new JobHistoryDto();
        jobHistory1.setEmployeeId(employeeId);
        jobHistory1.setStartDate(LocalDate.parse(startDate).minusDays(10));
        jobHistory1.setEndDate(LocalDate.parse(startDate));
        jobHistory1.setJobId("jobId1");
        jobHistory1.setDepartmentId(50);

        JobHistoryDto jobHistory2 = new JobHistoryDto();
        jobHistory2.setEmployeeId(employeeId);
        jobHistory2.setStartDate(LocalDate.parse(startDate).minusDays(100));
        jobHistory2.setEndDate(LocalDate.parse(startDate).minusDays(10));
        jobHistory2.setJobId("jobId2");
        jobHistory2.setDepartmentId(150);

        List<JobHistoryDto> jobHistories = List.of(jobHistory1, jobHistory2);

        given(jobHistoryService.findJobHistories(employeeId, LocalDate.parse(startDate)))
                .willReturn(jobHistories);

        //when & then
        mvc.perform(get(JOB_HISTORIES_PATH_URI, employeeId)
                        .param("startDate", startDate))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();
                    List<JobHistoryDto> responseJobHistories = objectMapper.readValue(responseBody, new TypeReference<>() {});

                    assertThat(responseJobHistories.size()).isEqualTo(jobHistories.size());
                    for(int i=0;i<jobHistories.size();i++) {
                        assertThat(responseJobHistories.get(i).getEmployeeId()).isEqualTo(jobHistories.get(i).getEmployeeId());
                        assertThat(responseJobHistories.get(i).getStartDate()).isEqualTo(jobHistories.get(i).getStartDate());
                        assertThat(responseJobHistories.get(i).getEndDate()).isEqualTo(jobHistories.get(i).getEndDate());
                        assertThat(responseJobHistories.get(i).getDepartmentId()).isEqualTo(jobHistories.get(i).getDepartmentId());
                        assertThat(responseJobHistories.get(i).getJobId()).isEqualTo(jobHistories.get(i).getJobId());
                    }
                });
    }

    @DisplayName("[특정 사원의 이력 조회 : 사원이 존재하지만 이력이 존재하지 않은 경우]")
    @Test
    void findJobHistoriesWithEmptyResult() throws Exception {

        //given
        Integer employeeId = 100;
        String startDate = "2024-11-23";

        given(jobHistoryService.findJobHistories(employeeId, LocalDate.parse(startDate)))
                .willReturn(List.of());

        //when & then
        mvc.perform(get(JOB_HISTORIES_PATH_URI, employeeId)
                        .param("startDate", startDate))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();

                    assertThat(responseBody).isEqualTo(NOT_FOUND_JOB_HISTORIES_MESSAGE);
                });
    }

    @DisplayName("[특정 사원의 이력 조회 : 사원이 존재하지 않은 경우]")
    @Test
    void findJobHistoriesWithNotExistingEmployee() throws Exception {

        //given
        Integer employeeId = 100;
        String startDate = "2024-11-23";

        given(jobHistoryService.findJobHistories(employeeId, LocalDate.parse(startDate)))
                .willThrow(new IllegalArgumentException(NOT_EXISTING_EMPLOYEE_MESSAGE));

        //when & then
        mvc.perform(get(JOB_HISTORIES_PATH_URI, employeeId)
                        .param("startDate", startDate))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseBody = result.getResponse().getContentAsString();

                    assertThat(responseBody).isEqualTo(NOT_EXISTING_EMPLOYEE_MESSAGE);
                });
    }
}