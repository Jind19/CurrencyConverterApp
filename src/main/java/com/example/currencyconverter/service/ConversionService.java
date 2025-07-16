package com.example.currencyconverter.service;

import com.example.currencyconverter.model.Conversion;
import com.example.currencyconverter.repository.ConversionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that handles business logic for currency conversions.
 * It interacts with the repository layer and external currency API.
 */
@Service
public class ConversionService {

    private final ConversionRepository conversionRepository;
    private final CurrencyApiClient currencyApiClient;

    /**
     * Constructor for injecting dependencies.
     *
     * @param conversionRepository repository for saving/retrieving Conversion records
     * @param currencyApiClient client to fetch exchange rates from external API
     */
    public ConversionService(ConversionRepository conversionRepository, CurrencyApiClient currencyApiClient) {
        this.conversionRepository = conversionRepository;
        this.currencyApiClient = currencyApiClient;
    }

    /**
     * Saves a new conversion record after calculating the exchange rate and converted amount.
     *
     * @param conversion The conversion input data
     * @return The saved Conversion object with exchange rate and converted amount
     */
    public Conversion saveConversion(Conversion conversion) {
        CurrencyApiClient.CurrencyExchangeInfo exchangeInfo = currencyApiClient.getExchangeRate(
                conversion.getSourceCurrency(), conversion.getTargetCurrency());

        double rate = exchangeInfo.getExchangeRate();
        double convertedAmount = conversion.getAmount() * rate;

        conversion.setExchangeRate(rate);
        conversion.setConvertedAmount(convertedAmount);

        return conversionRepository.save(conversion);
    }

    /**
     * Retrieves all stored currency conversion records from the database.
     *
     * @return list of all Conversion objects
     */
    public List<Conversion> getAllConversion() {
        return conversionRepository.findAll();
    }

    /**
     * Finds a conversion record by its unique ID.
     *
     * @param id the unique identifier of the conversion
     * @return an Optional containing the found Conversion, or empty if not found
     */
    public Optional<Conversion> getConversionById(String id) {
        return conversionRepository.findById(id);
    }

    /**
     * Deletes a conversion record by its ID.
     *
     * @param id the unique identifier of the conversion to delete
     */
    public void deleteConversion(String id) {
        conversionRepository.deleteById(id);
    }
}
