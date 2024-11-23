package com.echoandrich.task.department.service;

import com.echoandrich.task.department.dto.DepartmentLocationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.echoandrich.task.department.constants.DepartmentConstants.DEFAULT_DEPARTMENT_PAGING_SIZE;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@SpringBootTest
class DepartmentServiceIntegrationTest {

    @Autowired
    private DepartmentService departmentService;

    @DisplayName("[부서 정보 및 위치 정보 조회 : 연관 관계 Entity들 batch size로 가져와지는지 확인]")
    @Test
    void findDepartmentsWithLocation() {

        //given
        Integer departmentId = 1000;

        //when
        List<DepartmentLocationDto> departmentLocations = departmentService.findDepartmentsWithLocation(departmentId);

        //then
        System.out.println(departmentLocations);
        assertThat(departmentLocations.size()).isLessThanOrEqualTo(DEFAULT_DEPARTMENT_PAGING_SIZE);
        for (DepartmentLocationDto departmentLocation : departmentLocations) {
            assertThat(departmentLocation.getDepartmentId()).isLessThan(departmentId);
            departmentId = departmentLocation.getDepartmentId();
        }
    }

    @DisplayName("[부서 정보 및 위치 정보 조회 : 더이상 가져올 값이 없을 경우]")
    @Test
    void findDepartmentsWithLocationWithNotFound() {

        //given
        Integer departmentId = 1;

        //when
        List<DepartmentLocationDto> departmentLocations = departmentService.findDepartmentsWithLocation(departmentId);

        //then
        assertThat(departmentLocations).isEmpty();
    }
}