package com.example.mongodbapi.service.Impl;

import com.example.mongodbapi.request.RequestFromCoins;
import com.example.mongodbapi.response.ApiResponse;
import com.example.mongodbapi.view.View;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ServiceApiImplementationTest {
        @Mock
        private RestTemplate restTemplate;

        @InjectMocks
        private ServiceApiImplementation serviceApiImplementation;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void getAllAPI(){
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cache-Control", "no-cache");
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> requestEntity = new HttpEntity<>(headers);

            String responseBody = "[{\"name\":\"Bitcoin\",\"symbol\":\"BTC\"},{\"name\":\"Ethereum\",\"symbol\":\"ETH\"}]";

            ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

            when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(requestEntity), eq(String.class)))
                    .thenReturn(responseEntity);
            List<RequestFromCoins> expectedApi = List.of(
                    new RequestFromCoins("Bitcoin", "BTC"),
                    new RequestFromCoins("Ethereum", "ETH")
            );

            List<RequestFromCoins> api = serviceApiImplementation.getAllAPI();
            assertEquals(expectedApi, api);
            verify(restTemplate, times(1))
                    .exchange(anyString(), eq(HttpMethod.GET), eq(requestEntity), eq(String.class));
        }



    @Test
    void getCoinByName() {
        List<RequestFromCoins> mockResponse = List.of(
                new RequestFromCoins("Bitcoin", "BTC"),
                new RequestFromCoins("Ethereum", "ETH")
        );

        when(serviceApiImplementation.getAllAPI()).thenReturn(mockResponse);

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .message("rank uploaded")
                .localDateTime(LocalDateTime.now())
                .data(mockResponse)
                .build();

        ApiResponse apiResponse = serviceApiImplementation.getCoinByName("Bitcoin");

        assertEquals(expectedApiResponse, apiResponse);
        verify(serviceApiImplementation, times(1)).getAllAPI();
    }

    @Test
    void getValueByRank() {
    }

    @Test
    void getCoinByHighestPrice() {
    }
}