package com.echoandrich.task.jobHistory.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class JobHistoryId implements Serializable {

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
}
