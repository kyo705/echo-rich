package com.echoandrich.task.employee;

import com.echoandrich.task.department.Department;
import com.echoandrich.task.employee.dto.EmployeeUpdatingDto;
import com.echoandrich.task.employee.repository.Employee;
import com.echoandrich.task.job.Job;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

public class SetupEmployee {

    public static Employee employee(Integer employeeId) {

        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setFirstName("Steven");
        employee.setLastName("King");
        employee.setEmail("SKING");
        employee.setPhoneNumber("515.123.4567");
        employee.setHireDate(LocalDate.parse("1987-06-17"));
        employee.setJobId("AD_PRES");
        employee.setSalary(new BigDecimal("24000.00"));
        employee.setCommissionPct(null);
        employee.setManagerId(null);
        employee.setDepartmentId(90);

        return employee;
    }

    public static Stream<Arguments> getDefaultUpdatingConditions() {

        return Stream.of(
                Arguments.of(
                        EmployeeUpdatingDto.
                                builder()
                                .firstName("sunkyo")
                                .build()
                ),
                Arguments.of(
                        EmployeeUpdatingDto.
                                builder()
                                .lastName("lee")
                                .build()
                ),
                Arguments.of(
                        EmployeeUpdatingDto.
                                builder()
                                .email("LEE")
                                .build()
                ),
                Arguments.of(
                        EmployeeUpdatingDto.
                                builder()
                                .phoneNumber("010.1234.5678")
                                .build()
                ),
                Arguments.of(
                        EmployeeUpdatingDto.
                                builder()
                                .salary(BigDecimal.valueOf(50000))
                                .build()
                ),
                Arguments.of(
                        EmployeeUpdatingDto.
                                builder()
                                .commissionPct(BigDecimal.valueOf(0.30))
                                .build()
                )
        );
    }

    public static Job job(String jobId) {

        Job job = new Job();
        job.setJobId(jobId);
        job.setJobTitle("job title");
        job.setMinSalary(BigDecimal.valueOf(70000));
        job.setMaxSalary(BigDecimal.valueOf(100000));

        return job;
    }

    public static Department department(Integer departmentId) {

        Department department = new Department();
        department.setDepartmentId(departmentId);
        department.setDepartmentName("department name");
        department.setManagerId(600);
        department.setLocationId(300);

        return department;
    }

    public static Stream<String> invalidUpdatingConditions() {

        return Stream.of(
                                """
                                {
                                    "firstName" : ""
                                }
                                """,
                                """
                                {
                                    "firstName" : "123456789012345678901"
                                }
                                """,
                                """
                                {
                                    "lastName" : ""
                                }
                                """,
                                """
                                {
                                    "lastName" : "12345678901234567890123456"
                                }
                                """,
                """
                                {
                                    "email" : "12345678901234567890123456"
                                }
                                """,
                """
                                {
                                    "email" : "email"
                                }
                                """,
                """
                                {
                                    "email" : "EMAIL!"
                                }
                                """,
                """
                                {
                                    "phoneNumber" : "010-1234-5678"
                                }
                                """,
                """
                                {
                                    "phoneNumber" : "01012345678"
                                }
                                """,
                """
                                {
                                    "phoneNumber" : "010.12345678"
                                }
                                """,
                """
                                {
                                    "jobId" : ""
                                }
                                """,
                """
                                {
                                    "jobId" : "012345678901"
                                }
                                """,
                """
                                {
                                    "salary" : "zero"
                                }
                                """,
                """
                                {
                                    "salary" : "11111.111"
                                }
                                """,
                """
                                {
                                    "salary" : "1111111.11"
                                }
                                """,
                """
                                {
                                    "salary" : "111111111"
                                }
                                """,
                """
                                {
                                    "commissionPct" : "zero"
                                }
                                """,
                """
                                {
                                    "commissionPct" : "0.111"
                                }
                                """,
                """
                                {
                                    "commissionPct" : "111.0"
                                }
                                """,
                """
                                {
                                    "commissionPct" : "11.01"
                                }
                                """
                );
    }
}