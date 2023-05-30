package com.example.mongodbapi.service.Impl;

import com.example.mongodbapi.request.RequestFromCoins;
import com.example.mongodbapi.response.ApiResponse;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        List<RequestFromCoins> mockResponse = List.of(
                new RequestFromCoins("Bitcoin", "BTC", 1),
                new RequestFromCoins("Ethereum", "ETH", 2),
                new RequestFromCoins("Ripple", "XRP", 3)
        );

        when(serviceApiImplementation.getAllAPI()).thenReturn(mockResponse);

        int capRank = 2;

        Optional<RequestFromCoins> expectedCoin = mockResponse.stream()
                .filter(c -> c.getMarket_cap_rank() == capRank)
                .findFirst();

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .message("rank uploaded")
                .localDateTime(LocalDateTime.now())
                .data(expectedCoin.get().getId())
                .build();

        ApiResponse apiResponse = serviceApiImplementation.getValueByRank(capRank);

        assertEquals(expectedApiResponse, apiResponse);
        verify(serviceApiImplementation, times(1)).getAllAPI();
    }

    @Test
    void getCoinByHighestPrice() {

        List<RequestFromCoins> mockResponse = List.of(
                new RequestFromCoins("Bitcoin", "BTC", 50000),
                new RequestFromCoins("Ethereum", "ETH", 3500),
                new RequestFromCoins("Ripple", "XRP", 1)
        );

        when(serviceApiImplementation.getAllAPI()).thenReturn(mockResponse);

        ApiResponse expectedApiResponse = ApiResponse.builder()
                .message("rank uploaded")
                .localDateTime(LocalDateTime.now())
                .data(mockResponse)
                .build();

        ApiResponse apiResponse = serviceApiImplementation.getCoinByHighestPrice();

        assertEquals(expectedApiResponse, apiResponse);
        verify(serviceApiImplementation, times(1)).getAllAPI();
    }
}