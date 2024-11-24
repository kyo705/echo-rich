package com.echoandrich.task.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentsFindingDto {

    @Schema(defaultValue = "Integer.MAX_VALUE", description = "페이징을 위한 값 : 마지막으로 읽은 부서 id")
    private Integer lastDepartmentId = Integer.MAX_VALUE;
}
