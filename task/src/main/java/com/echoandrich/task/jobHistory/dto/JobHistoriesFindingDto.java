package com.echoandrich.task.jobHistory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;

import static com.echoandrich.task.common.constants.DateTimeConstants.ASIA_SEOUL_TIME_ZONE;

@Getter
@Setter
public class JobHistoriesFindingDto {

    @Schema(defaultValue = "현재 시각", format = "ISO Date Format")
    private LocalDate startDate = LocalDate.now(ZoneId.of(ASIA_SEOUL_TIME_ZONE));
}
