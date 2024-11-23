package com.echoandrich.task.jobHistory.dto;

import com.echoandrich.task.jobHistory.repository.JobHistory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JobHistoryDto {

    private Integer employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String jobId;
    private Integer departmentId;

    public static JobHistoryDto create(JobHistory jobHistory) {

        JobHistoryDto jobHistoryDto = new JobHistoryDto();
        jobHistoryDto.setEmployeeId(jobHistory.getId().getEmployeeId());
        jobHistoryDto.setStartDate(jobHistory.getId().getStartDate());
        jobHistoryDto.setEndDate(jobHistory.getEndDate());
        jobHistoryDto.setJobId(jobHistory.getJobId());
        jobHistoryDto.setDepartmentId(jobHistory.getDepartmentId());

        return jobHistoryDto;
    }
}
