package com.echoandrich.task.jobHistory.controller;

import com.echoandrich.task.common.error.NotFoundException;
import com.echoandrich.task.jobHistory.dto.JobHistoriesFindingDto;
import com.echoandrich.task.jobHistory.dto.JobHistoryDto;
import com.echoandrich.task.jobHistory.service.JobHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.echoandrich.task.jobHistory.constants.JobHistoryConstants.JOB_HISTORIES_PATH_URI;
import static com.echoandrich.task.jobHistory.constants.JobHistoryConstants.NOT_FOUND_JOB_HISTORIES_MESSAGE;

@RestController
@RequiredArgsConstructor
public class JobHistoryController {

    private final JobHistoryService jobHistoryService;

    @GetMapping(JOB_HISTORIES_PATH_URI)
    public ResponseEntity<List<JobHistoryDto>> findJobHistories(@PathVariable Integer employeeId,
                                                                @ModelAttribute JobHistoriesFindingDto requestParams) {

        List<JobHistoryDto> jobHistories = jobHistoryService.findJobHistories(employeeId, requestParams.getStartDate());
        if(jobHistories.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_JOB_HISTORIES_MESSAGE);
        }

        return ResponseEntity
                .ok(jobHistories);
    }
}
