package com.echoandrich.task.ktx.repository;

import com.echoandrich.task.ktx.constants.KtxConstants;
import com.echoandrich.task.ktx.dto.TrainItemDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.echoandrich.task.common.constants.DateTimeConstants.ASIA_SEOUL_TIME_ZONE;
import static com.echoandrich.task.ktx.constants.KtxConstants.OPEN_API_TRAIN_INFO_PATH_URI;

@Repository
@RequiredArgsConstructor
public class KtxRepository {

    private final WebClient webClient = WebClient.builder()
            .baseUrl(KtxConstants.OPEN_API_BASE_URL)
            .build();

    private final ObjectMapper objectMapper;

    @Value("${openapi.ktx.secret}")
    private String secret;

    public List<TrainItemDto> findTrainInfo(String departure, String arrival, Integer offset, Integer size) {

        String date = LocalDateTime.now(ZoneId.of(ASIA_SEOUL_TIME_ZONE)).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Map<String, Object> response =  webClient.get()
                .uri(uri -> uri.path(OPEN_API_TRAIN_INFO_PATH_URI)
                        .queryParam("serviceKey", secret)
                        .queryParam("pageNo", offset)
                        .queryParam("numOfRows", size)
                        .queryParam("_type", "json")
                        .queryParam("depPlaceId", departure)
                        .queryParam("arrPlaceId", arrival)
                        .queryParam("depPlandTime", date)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        Map<String, Object> body = (Map<String, Object>) response.get("response");
        Map<String, Object> items = (Map<String, Object>) ((Map<String, Object>) body.get("body")).get("items");
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");

        return objectMapper.convertValue(itemList, new TypeReference<>() {});
    }
}
