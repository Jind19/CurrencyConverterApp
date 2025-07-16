package com.example.currencyconverter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * A Spring component that communicates with an external currency exchange API
 * to retrieve exchange rates between two currencies.
 */
@Component
public class CurrencyApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * API key for authenticating requests to the currency API.
     */
    @Value("${freecurrency.api.key}")
    private String apiKey;

    /**
     * Base URL of the currency exchange API.
     */
    @Value("${freecurrency.api.url}")
    private String apiUrl;

    /**
     * Constructor for injecting RestTemplate and initializing ObjectMapper.
     *
     * @param restTemplate RestTemplate for HTTP requests
     */
    public CurrencyApiClient(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Fetches the exchange rate between the source and target currency
     * from the external API.
     *
     * @param sourceCurrency the base currency (e.g. "USD")
     * @param targetCurrency the target currency (e.g. "EUR")
     * @return a CurrencyExchangeInfo object containing the exchange rate
     */
    public CurrencyExchangeInfo getExchangeRate(String sourceCurrency, String targetCurrency){

        String url = String.format(
                "%s?apikey=%s&currencies=%s&base_currency=%s",
                apiUrl, apiKey, targetCurrency, sourceCurrency );

        try{
            // Make API call
            String response = restTemplate.getForObject(url, String.class);

            // Parse JSON response
            JsonNode root = objectMapper.readTree(response);

            double exchangeRate = root.path("data").path(targetCurrency).asDouble();

            return new CurrencyExchangeInfo(exchangeRate);
        } catch (Exception e){
            // Handle any parsing or network errors
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }
    }

    /**
     * A simple class representing exchange rate information.
     */
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
