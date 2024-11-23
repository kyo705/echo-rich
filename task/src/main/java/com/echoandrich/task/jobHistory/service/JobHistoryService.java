package com.echoandrich.task.jobHistory.service;

import com.echoandrich.task.employee.repository.EmployeeRepository;
import com.echoandrich.task.jobHistory.dto.JobHistoryDto;
import com.echoandrich.task.jobHistory.repository.JobHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.echoandrich.task.employee.constants.EmployeeConstants.NOT_EXISTING_EMPLOYEE_MESSAGE;
import static com.echoandrich.task.jobHistory.constants.JobHistoryConstants.DEFAULT_PAGING_SIZE;

@Service
@RequiredArgsConstructor
public class JobHistoryService {

    private final EmployeeRepository employeeRepository;

    private final JobHistoryRepository jobHistoryRepository;


    @Transactional(readOnly = true)
    public List<JobHistoryDto> findJobHistories(Integer employeeId, LocalDate startDate) {

        // employee 존재 여부 조회
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTING_EMPLOYEE_MESSAGE));

        // 최신 순으로 데이터 조회
        PageRequest paging = PageRequest.ofSize(DEFAULT_PAGING_SIZE)
                .withSort(Sort.by(Sort.Order.desc("id.startDate")));

        return Optional.ofNullable(jobHistoryRepository.findByIdEmployeeIdAndIdStartDateBefore(employeeId, startDate, paging))
                .orElseGet(List::of)
                .stream()
                .map(JobHistoryDto::create)
                .toList();
    }
}
