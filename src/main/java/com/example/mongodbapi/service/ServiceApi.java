package com.example.mongodbapi.service;
import com.example.mongodbapi.request.RequestFromCoins;

import java.util.*;

public interface ServiceApi {
    List<RequestFromCoins> getAllAPI();
    List<RequestFromCoins> getCoinByName(String coin);

    String getValueByRank(int capRank);
    List<RequestFromCoins> getCoinByHighestPrice();


}
