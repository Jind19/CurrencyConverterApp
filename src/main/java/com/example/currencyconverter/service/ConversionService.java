package com.example.currencyconverter.service;

import com.example.currencyconverter.model.Conversion;
import com.example.currencyconverter.repository.ConversionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversionService {

    private final ConversionRepository conversionRepository;
    private final CurrencyApiClient currencyApiClient;

    public ConversionService(ConversionRepository conversionRepository, CurrencyApiClient currencyApiClient) {
        this.conversionRepository = conversionRepository;
        this.currencyApiClient = currencyApiClient;
    }

    public Conversion saveConversion(Conversion conversion) {
        CurrencyApiClient.CurrencyExchangeInfo currencyExchangeInfo = currencyApiClient.getExchangeRate(
                conversion.getSourceCurrency(), conversion.getTargetCurrency());
        conversion.setExchangeRate(currencyExchangeInfo.getExchangeRate());
        return conversionRepository.insert(conversion);
    }

    public List<Conversion> getAllConversion() {
        return conversionRepository.findAll();
    }


    public Optional<Conversion> getConversionById(String id) {
        return conversionRepository.findById(id);
    }

    public void deleteConversion(String id) {
        conversionRepository.deleteById(id);
    }
}
