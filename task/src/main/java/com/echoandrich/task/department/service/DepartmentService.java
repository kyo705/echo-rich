package com.echoandrich.task.department.service;

import com.echoandrich.task.department.dto.DepartmentLocationDto;
import com.echoandrich.task.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.echoandrich.task.department.constants.DepartmentConstants.DEFAULT_DEPARTMENT_PAGING_SIZE;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<DepartmentLocationDto> findDepartmentsWithLocation(Integer lastDepartmentId) {

        PageRequest paging = PageRequest.ofSize(DEFAULT_DEPARTMENT_PAGING_SIZE)
                .withSort(Sort.by(Sort.Order.desc("departmentId")));

        return Optional.ofNullable(departmentRepository.findByDepartmentIdBefore(lastDepartmentId, paging))
                .orElseGet(List::of)
                .stream()
                .map(DepartmentLocationDto::create)
                .toList();
    }
}
