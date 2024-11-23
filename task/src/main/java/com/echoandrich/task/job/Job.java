package com.echoandrich.task.job;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @Column(name = "job_id", length = 10, nullable = false)
    private String jobId;

    @Column(name = "job_title", length = 35, nullable = false)
    private String jobTitle;

    @Column(name = "min_salary", precision = 8, scale = 0)
    private BigDecimal minSalary;

    @Column(name = "max_salary", precision = 8, scale = 0)
    private BigDecimal maxSalary;
}
