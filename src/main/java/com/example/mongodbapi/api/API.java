package com.example.mongodbapi.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter

public class API {
    private static final String API_URL="https://api.coingecko.com/api/v3/coins/markets?vs_currency=eth";

    public static String getApiUrl(){
        return API_URL;
    }
}
