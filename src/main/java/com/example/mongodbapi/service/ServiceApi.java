package com.example.mongodbapi.service;
import com.example.mongodbapi.request.RequestFromCoins;
import com.example.mongodbapi.response.ApiResponse;

import java.util.*;

public interface ServiceApi {
    List<RequestFromCoins> getAllAPI();
    ApiResponse getCoinByName(String coin);

    ApiResponse getValueByRank(int capRank);
    ApiResponse getCoinByHighestPrice();


}
