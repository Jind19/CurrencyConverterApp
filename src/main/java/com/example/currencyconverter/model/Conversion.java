package com.example.currencyconverter.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * This class represents a currency conversion record stored in MongoDB.
 * It uses Lombok to generate boilerplate code like getters, setters, constructors, etc.
 * Validation annotations ensure proper input data, and MongoDB annotations map the object to a collection.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "conversions")  // Maps this class to the "conversions" collection in MongoDB
public class Conversion {

    @Id  // Marks this field as the primary key (_id) in MongoDB
    private String id;

    @NotBlank(message = "Source currency is required")
    private String sourceCurrency; // e.g., "USD"

    @NotBlank(message = "Target currency is required")
    private String targetCurrency; // e.g., "EUR"

    @Min(value = 1, message = "Amount must be greater than 0")
    private double amount; // Original amount to convert

    private double exchangeRate;  // Rate used for conversion, e.g., 1.12
    private double convertedAmount;  // Final result after applying exchange rate

}
