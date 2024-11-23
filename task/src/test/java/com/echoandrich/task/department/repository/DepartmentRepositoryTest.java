package com.echoandrich.task.department.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.echoandrich.task.department.constants.DepartmentConstants.DEFAULT_DEPARTMENT_PAGING_SIZE;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @DisplayName("[부서 조회 : 존재하는 부서인 경우]")
    @Test
    public void findById() {

        //given
        Integer departmentId = 100;

        //when
        Optional<Department> department = departmentRepository.findById(departmentId);

        //then
        assertThat(department).isPresent();
        assertThat(department.get().getDepartmentId()).isEqualTo(departmentId);
        assertThat(department.get().getDepartmentName()).isEqualTo("Finance");
        assertThat(department.get().getManagerId()).isEqualTo(108);
        assertThat(department.get().getLocation().getLocationId()).isEqualTo(1700);
    }

    @DisplayName("[부서 조회 : 존재하지 않는 부서 경우]")
    @Test
    public void findByIdWithNotExistingDepartmentId() {

        //given
        Integer departmentId = 0;

        //when
        Optional<Department> department = departmentRepository.findById(departmentId);

        //then
        assertThat(department).isNotPresent();
    }

    @DisplayName("[부서 조회 : 존재하지 않는 부서 경우]")
    @Test
    public void findByDepartmentIdBefore() {

        //given
        Integer departmentId = 0;
        PageRequest paging = PageRequest.ofSize(DEFAULT_DEPARTMENT_PAGING_SIZE)
                .withSort(Sort.by(Sort.Order.desc("departmentId")));;

        //when
        List<Department> department = departmentRepository.findByDepartmentIdBefore(departmentId, paging);

        //then
        System.out.println(department);
    }
}