package com.example.mongodbapi.service.Impl;

import com.example.mongodbapi.request.RequestFromCoins;
import com.example.mongodbapi.response.ApiResponse;
import com.example.mongodbapi.service.ServiceApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.example.mongodbapi.api.API.getApiUrl;

@Service
@RequiredArgsConstructor
public class ServiceApiImplementation implements ServiceApi {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    @Override
    public List<RequestFromCoins> getAllAPI() {
        headers.set("Cache-Control", "no-cache");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(getApiUrl(), HttpMethod.GET, requestEntity, String.class);
        String responseBody = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        List<RequestFromCoins> api = null;
        try {
            api = objectMapper.readValue(responseBody, new TypeReference<List<RequestFromCoins>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return api;
    }

    @Override
    public ApiResponse getCoinByName(String coin) {
        List<RequestFromCoins> response = getAllAPI();
        response.stream()
                .filter(c-> {
                     return c.getId().equals(coin);
                }).toList();

        return ApiResponse.builder()
                .message("rank uploaded")
                .localDateTime(LocalDateTime.now())
                .data(response)
                .build();
    }
    @Override
    public ApiResponse getValueByRank(int capRank) {
        List<RequestFromCoins>  result =  getAllAPI();
        result.stream()
                .filter(c -> c.getMarket_cap_rank() == capRank)
                .findFirst();
        String response = result.stream().map(RequestFromCoins::getId).findFirst().get();
        return ApiResponse.builder()
                .message("rank uploaded")
                .localDateTime(LocalDateTime.now())
                .data(response)
                .build();
    }

    @Override
    public ApiResponse getCoinByHighestPrice() {
        List<RequestFromCoins> response =  getAllAPI().stream()
                .sorted(Comparator.comparingDouble(RequestFromCoins::getCurrent_price).reversed())
                .toList();
        return ApiResponse.builder()
                .message("rank uploaded")
                .localDateTime(LocalDateTime.now())
                .data(response)
                .build();
    }


}
