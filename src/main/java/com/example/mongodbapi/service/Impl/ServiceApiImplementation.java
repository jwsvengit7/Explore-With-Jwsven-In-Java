package com.example.mongodbapi.service.Impl;

import com.example.mongodbapi.request.RequestFromCoins;
import com.example.mongodbapi.service.ServiceApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.example.mongodbapi.api.API.getApiUrl;

@Service
@RequiredArgsConstructor
public class ServiceApiImplementation implements ServiceApi {
    private final ModelMapper modelMapper;
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
    public List<RequestFromCoins> getCoinByName(String coin) {
       return getAllAPI().stream()
                .filter(c-> {
                     return c.getId().equals(coin);
                }).toList();
    }
    public String getValueByRank(int capRank) {
        Optional<RequestFromCoins> result = getAllAPI().stream()
                .filter(c -> c.getMarket_cap_rank() == capRank)
                .findFirst();
        return result.map(RequestFromCoins::getId).orElse(null);
    }

    @Override
    public List<RequestFromCoins> getCoinByHighestPrice() {
        return getAllAPI().stream()
                .sorted(Comparator.comparingDouble(RequestFromCoins::getCurrent_price).reversed())
                .toList();
    }


}
