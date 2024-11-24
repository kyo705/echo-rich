package com.echoandrich.task.ktx.service;

import com.echoandrich.task.ktx.dto.TrainItemDto;
import com.echoandrich.task.ktx.dto.TrainStationDto;
import com.echoandrich.task.ktx.repository.KtxRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.echoandrich.task.ktx.constants.KtxConstants.NOT_EXISTING_TRAIN_STATION_MESSAGE;

@Service
@RequiredArgsConstructor
public class KtxService {

    private final Map<String, String> trainStationMap = new HashMap<>();

    private final ObjectMapper objectMapper;

    private final KtxRepository ktxRepository;


    @PostConstruct
    public void setupStation() {
        try {
            // 1. JSON 파일을 리소스 디렉토리에서 읽기
            ClassPathResource resource = new ClassPathResource("json/station.json");

            // 2. 파일 내용을 DTO로 매핑
            List<TrainStationDto> trainStations = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
            for(TrainStationDto trainStation : trainStations) {
                trainStationMap.put(trainStation.getStationName(), trainStation.getStationCode());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read and parse JSON file", e);
        }
    }

    public List<TrainItemDto> findTrainInfo(String departure, String arrival, Integer offset, Integer size) {

        String departureCode = Optional.ofNullable(trainStationMap.get(departure))
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTING_TRAIN_STATION_MESSAGE));

        String arrivalCode = Optional.ofNullable(trainStationMap.get(arrival))
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXISTING_TRAIN_STATION_MESSAGE));

        return ktxRepository.findTrainInfo(departureCode, arrivalCode, offset, size);
    }
 }
