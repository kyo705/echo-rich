package com.echoandrich.task.ktx.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainInfoFindingDto {

    @NotBlank
    private String departure;

    @NotBlank
    private String arrival;

    @Positive
    private Integer offset = 1;

    @Positive
    private Integer size = 20;
}
