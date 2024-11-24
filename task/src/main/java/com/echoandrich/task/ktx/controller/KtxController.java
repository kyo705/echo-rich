package com.echoandrich.task.ktx.controller;

import com.echoandrich.task.ktx.dto.TrainInfoFindingDto;
import com.echoandrich.task.ktx.dto.TrainItemDto;
import com.echoandrich.task.ktx.service.KtxService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.echoandrich.task.ktx.constants.KtxConstants.TRAIN_INFO_PATH_URI;

@RestController
@RequiredArgsConstructor
public class KtxController {

    private final KtxService ktxService;

    @GetMapping(TRAIN_INFO_PATH_URI)
    public ResponseEntity<List<TrainItemDto>> findTrainInfos(
            @ParameterObject @ModelAttribute @Valid TrainInfoFindingDto requestParam) {

        List<TrainItemDto> responseBody = ktxService.findTrainInfo(
                requestParam.getDeparture(),
                requestParam.getArrival(),
                requestParam.getOffset(),
                requestParam.getSize()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
}
