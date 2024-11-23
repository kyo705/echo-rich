package com.echoandrich.task.department;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Column(name = "department_name", length = 30, nullable = false)
    private String departmentName;

    @Column(name = "location_id", nullable = false)
    private Integer locationId;

    @Column(name = "manager_id", nullable = false)
    private Integer managerId;
}