package com.echoandrich.task.ktx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainItemDto {

    @JsonProperty("adultcharge")
    private int adultCharge;       // adultcharge

    @JsonProperty("arrplacename")
    private String arrPlaceName;   // arrplacename

    @JsonProperty("arrplandtime")
    private long arrPlanDTime;     // arrplandtime

    @JsonProperty("depplacename")
    private String depPlaceName;   // depplacename

    @JsonProperty("depplandtime")
    private long depPlanDTime;     // depplandtime

    @JsonProperty("traingradename")
    private String trainGradeName; // traingradename

    @JsonProperty("trainno")
    private int trainNo;           // trainno
}