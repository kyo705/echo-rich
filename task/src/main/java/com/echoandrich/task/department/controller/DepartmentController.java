package com.echoandrich.task.department.controller;

import com.echoandrich.task.common.error.NotFoundException;
import com.echoandrich.task.department.dto.DepartmentLocationDto;
import com.echoandrich.task.department.dto.DepartmentsFindingDto;
import com.echoandrich.task.department.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.echoandrich.task.department.constants.DepartmentConstants.DEPARTMENTS_PATH_URI;
import static com.echoandrich.task.department.constants.DepartmentConstants.NOT_FOUND_DEPARTMENTS_MESSAGE;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping(DEPARTMENTS_PATH_URI)
    public ResponseEntity<List<DepartmentLocationDto>> findDepartmentsWithLocation(
            @ModelAttribute @Valid DepartmentsFindingDto requestParams) {

        List<DepartmentLocationDto> responseBody = departmentService.findDepartmentsWithLocation(requestParams.getLastDepartmentId());

        if(responseBody.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_DEPARTMENTS_MESSAGE);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
}
