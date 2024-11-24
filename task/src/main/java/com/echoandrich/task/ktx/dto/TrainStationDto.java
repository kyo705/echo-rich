package com.echoandrich.task.ktx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainStationDto {

    @JsonProperty("nodename")
    private String stationName;

    @JsonProperty("nodeid")
    private String stationCode;
}
