package com.echoandrich.task.jobHistory.repository;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
@Setter
@Entity
@Table(name = "job_history")
public class JobHistory {

    @EmbeddedId
    private JobHistoryId id;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "job_id", length = 10, nullable = false)
    private String jobId;

    @Column(name = "department_id")
    private Integer departmentId;
}
