package com.echoandrich.task.department;

import com.echoandrich.task.department.dto.DepartmentLocationDto;

import java.util.List;

public class SetupDepartment {

    public static List<DepartmentLocationDto> departmentLocations() {

        return List.of(
                DepartmentLocationDto
                        .builder()
                        .departmentId(5)
                        .departmentName("department name5")
                        .regionName("region name5")
                        .countryName("country name5")
                        .build(),

                DepartmentLocationDto
                        .builder()
                        .departmentId(4)
                        .departmentName("department name4")
                        .regionName("region name4")
                        .countryName("country name4")
                        .build(),

                DepartmentLocationDto
                        .builder()
                        .departmentId(3)
                        .departmentName("department name3")
                        .regionName("region name3")
                        .countryName("country name3")
                        .build()
        );
    }
}
