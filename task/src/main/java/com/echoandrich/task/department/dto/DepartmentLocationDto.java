package com.echoandrich.task.department.dto;

import com.echoandrich.task.department.repository.Department;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentLocationDto {

    private Integer departmentId;
    private String departmentName;
    private Integer managerId;
    private Integer locationId;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;
    private String countryName;
    private String regionName;

    public static DepartmentLocationDto create(Department department) {

        DepartmentLocationDto departmentLocationDto = new DepartmentLocationDto();
        departmentLocationDto.setDepartmentId(department.getDepartmentId());
        departmentLocationDto.setDepartmentName(department.getDepartmentName());
        departmentLocationDto.setManagerId(department.getManagerId());
        departmentLocationDto.setLocationId(department.getLocation().getLocationId());
        departmentLocationDto.setStreetAddress(department.getLocation().getStreetAddress());
        departmentLocationDto.setPostalCode(department.getLocation().getPostalCode());
        departmentLocationDto.setCity(department.getLocation().getCity());
        departmentLocationDto.setStateProvince(department.getLocation().getStateProvince());
        departmentLocationDto.setCountryName(department.getLocation().getCountry().getCountryName());
        departmentLocationDto.setRegionName(department.getLocation().getCountry().getRegion().getRegionName());

        return departmentLocationDto;
    }
}
