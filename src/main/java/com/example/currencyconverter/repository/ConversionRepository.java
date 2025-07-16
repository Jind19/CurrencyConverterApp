package com.example.currencyconverter.repository;

import com.example.currencyconverter.model.Conversion;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for the Conversion model.
 */
public interface ConversionRepository extends MongoRepository<Conversion, String> {

}
