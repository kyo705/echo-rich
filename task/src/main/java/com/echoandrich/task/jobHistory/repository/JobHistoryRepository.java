package com.echoandrich.task.jobHistory.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, JobHistoryId> {

    List<JobHistory> findByIdEmployeeIdAndIdStartDateBefore(
            Integer employeeId,
            LocalDate startDate,
            Pageable pageable
    );
}
