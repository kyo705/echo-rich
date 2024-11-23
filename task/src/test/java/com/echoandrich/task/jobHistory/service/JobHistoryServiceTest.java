package com.echoandrich.task.jobHistory.service;

import com.echoandrich.task.employee.repository.Employee;
import com.echoandrich.task.employee.repository.EmployeeRepository;
import com.echoandrich.task.jobHistory.dto.JobHistoryDto;
import com.echoandrich.task.jobHistory.repository.JobHistory;
import com.echoandrich.task.jobHistory.repository.JobHistoryId;
import com.echoandrich.task.jobHistory.repository.JobHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.echoandrich.task.employee.constants.EmployeeConstants.NOT_EXISTING_EMPLOYEE_MESSAGE;
import static com.echoandrich.task.jobHistory.constants.JobHistoryConstants.DEFAULT_PAGING_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JobHistoryServiceTest {

    @InjectMocks
    private JobHistoryService jobHistoryService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private JobHistoryRepository jobHistoryRepository;


    @DisplayName("[특정 사원의 이력 조회 : 사원이 존재하는 경우]")
    @Test
    void findJobHistories() {

        //given
        Integer employeeId = 100;
        LocalDate startDate = LocalDate.parse("2024-11-23");

        JobHistoryId jobHistoryId1 = new JobHistoryId();
        jobHistoryId1.setEmployeeId(employeeId);
        jobHistoryId1.setStartDate(startDate.minusDays(10));

        JobHistory jobHistory1 = new JobHistory();
        jobHistory1.setId(jobHistoryId1);
        jobHistory1.setEndDate(startDate);
        jobHistory1.setJobId("jobId1");
        jobHistory1.setDepartmentId(50);

        JobHistoryId jobHistoryId2 = new JobHistoryId();
        jobHistoryId2.setEmployeeId(employeeId);
        jobHistoryId2.setStartDate(startDate.minusDays(100));

        JobHistory jobHistory2 = new JobHistory();
        jobHistory2.setId(jobHistoryId2);
        jobHistory2.setEndDate(startDate.minusDays(10));
        jobHistory2.setJobId("jobId2");
        jobHistory2.setDepartmentId(150);

        List<JobHistory> jobHistories = List.of(jobHistory1, jobHistory2);
        PageRequest paging = PageRequest.ofSize(DEFAULT_PAGING_SIZE).withSort(Sort.by(Sort.Order.desc("id.startDate")));

        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(new Employee()));
        given(jobHistoryRepository.findByIdEmployeeIdAndIdStartDateBefore(employeeId, startDate, paging))
                .willReturn(jobHistories);

        //when
        List<JobHistoryDto> result = jobHistoryService.findJobHistories(employeeId, startDate);

        //then
        assertThat(result.size()).isEqualTo(jobHistories.size());
        for(int i=0;i<jobHistories.size();i++) {
            assertThat(result.get(i).getEmployeeId()).isEqualTo(jobHistories.get(i).getId().getEmployeeId());
            assertThat(result.get(i).getStartDate()).isEqualTo(jobHistories.get(i).getId().getStartDate());
            assertThat(result.get(i).getEndDate()).isEqualTo(jobHistories.get(i).getEndDate());
            assertThat(result.get(i).getDepartmentId()).isEqualTo(jobHistories.get(i).getDepartmentId());
            assertThat(result.get(i).getJobId()).isEqualTo(jobHistories.get(i).getJobId());
        }
    }

    @DisplayName("[특정 사원의 이력 조회 : 사원이 존재하지만 이력이 존재하지 않은 경우]")
    @Test
    void findJobHistoriesWithEmptyHistories() {

        //given
        Integer employeeId = 100;
        LocalDate startDate = LocalDate.parse("2024-11-23");
        PageRequest paging = PageRequest.ofSize(DEFAULT_PAGING_SIZE).withSort(Sort.by(Sort.Order.desc("id.startDate")));

        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(new Employee()));
        given(jobHistoryRepository.findByIdEmployeeIdAndIdStartDateBefore(employeeId, startDate, paging))
                .willReturn(List.of());

        //when
        List<JobHistoryDto> result = jobHistoryService.findJobHistories(employeeId, startDate);

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("[특정 사원의 이력 조회 : 사원이 존재하지만 이력이 존재하지 않은 경우 2]")
    @Test
    void findJobHistoriesWithEmptyHistories2() {

        //given
        Integer employeeId = 100;
        LocalDate startDate = LocalDate.parse("2024-11-23");
        PageRequest paging = PageRequest.ofSize(DEFAULT_PAGING_SIZE).withSort(Sort.by(Sort.Order.desc("id.startDate")));

        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(new Employee()));
        given(jobHistoryRepository.findByIdEmployeeIdAndIdStartDateBefore(employeeId, startDate, paging))
                .willReturn(null);

        //when
        List<JobHistoryDto> result = jobHistoryService.findJobHistories(employeeId, startDate);

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("[특정 사원의 이력 조회 : 사원이 존재하지 않은 경우]")
    @Test
    void findJobHistoriesWithNotExistingEmployee() {

        //given
        Integer employeeId = 100;
        LocalDate startDate = LocalDate.parse("2024-11-23");

        given(employeeRepository.findById(employeeId)).willReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> jobHistoryService.findJobHistories(employeeId, startDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NOT_EXISTING_EMPLOYEE_MESSAGE);
    }
}