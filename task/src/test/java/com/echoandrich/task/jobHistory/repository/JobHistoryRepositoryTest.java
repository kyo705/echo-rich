package com.echoandrich.task.jobHistory.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class JobHistoryRepositoryTest {

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @DisplayName("[특정 사원의 이력 조회 : 조건에 맞는 값이 있을 경우]")
    @Test
    void findByIdEmployeeIdAndIdStartDateBefore() {

        //given
        Integer employeeId = 200;
        LocalDate startDate = LocalDate.parse("2024-11-23");
        PageRequest pageRequest = PageRequest.ofSize(1).withSort(Sort.by(Sort.Order.desc("id.startDate")));

        //when
        List<JobHistory> jobHistories = jobHistoryRepository.findByIdEmployeeIdAndIdStartDateBefore(employeeId, startDate, pageRequest);

        //then
        assertThat(jobHistories.size()).isEqualTo(1);
        assertThat(jobHistories.get(0).getId().getStartDate()).isBefore(startDate);
    }

    @DisplayName("[특정 사원의 이력 조회 : 조건에 맞는 값이 없을 경우]")
    @Test
    void findByIdEmployeeIdAndIdStartDateBeforeWithNotFoundConditions() {

        //given
        Integer employeeId = 200;
        LocalDate startDate = LocalDate.parse("1987-09-17");
        PageRequest pageRequest = PageRequest.ofSize(1).withSort(Sort.by(Sort.Order.desc("id.startDate")));

        //when
        List<JobHistory> jobHistories = jobHistoryRepository.findByIdEmployeeIdAndIdStartDateBefore(employeeId, startDate, pageRequest);

        //then
        assertThat(jobHistories).isEmpty();
    }
}