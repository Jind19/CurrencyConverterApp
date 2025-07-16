package com.example.currencyconverter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("$freecurrency.api.key")
    private String apiKey;

    @Value("$freecurrency.api.url")
    private String apiUrl;

    public CurrencyApiClient(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public CurrencyExchangeInfo getExchangeRate(String sourceCurrency, String targetCurrency){

        String url = String.format(
                "%s?apikey=%s&currencies=%s&base_currency=%s",
                apiUrl, apiKey, targetCurrency, sourceCurrency );

        try{
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            double exchangeRate = root.path("data").path(targetCurrency).asDouble();

            return new CurrencyExchangeInfo(exchangeRate);
        } catch (Exception e){
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }
    }


    public static class CurrencyExchangeInfo {

        private double exchangeRate;

        public CurrencyExchangeInfo(double exchangeRate){
            this.exchangeRate = exchangeRate;
        }
        public double getExchangeRate() {
            return exchangeRate;
        }

    }


}
