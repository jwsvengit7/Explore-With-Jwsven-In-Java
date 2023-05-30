package com.example.mongodbapi.request;

import lombok.Data;
@Data

public class RequestFromCoins {

    private String id;
    private String symbol;
    private String name;
    private String image;
    private float current_price;
    private Long market_cap;
    private Integer market_cap_rank;
    private Long fully_diluted_valuation;
    private Long total_volume;
    private float high_24h;
    private float low_24h;
    private float price_change_24h;
    private float price_change_percentage_24h;
    private float market_cap_change_24h;
    private float market_cap_change_percentage_24h;
    private float circulating_supply;
    private float total_supply;
    private float max_supply;
    private float ath;
    private float ath_change_percentage;
    private String ath_date;
    private String atl_date;
    private float atl;
    private float atl_change_percentage;
    private Object roi;
    private String last_updated;


}
