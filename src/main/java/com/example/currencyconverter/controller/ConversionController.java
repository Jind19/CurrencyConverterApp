package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.Conversion;
import com.example.currencyconverter.service.ConversionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing currency conversions.
 *
 * This controller handles HTTP requests for creating, reading,
 * updating, and deleting Conversion records stored in MongoDB.
 */
@RestController
@RequestMapping("/api/conversions")
public class ConversionController {

    private final ConversionService conversionService;

    /**
     * Constructor-based dependency injection of ConversionService.
     *
     * @param conversionService the service handling business logic
     */
    @Autowired
    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * Add a new conversion record to the database.
     *
     * @param conversion the conversion data to save
     * @return the saved Conversion object
     */
    @PostMapping
    public Conversion addNewConversion(@Valid @RequestBody Conversion conversion){
        return conversionService.saveConversion(conversion);
    }

    /**
     * Get a specific conversion by its ID.
     *
     * @param id the ID of the conversion to retrieve
     * @return ResponseEntity containing the conversion if found, otherwise 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Conversion> getConversionById(@PathVariable String id){
        Optional<Conversion> conversion = conversionService.getConversionById(id);
        if(conversion.isPresent()){
            return ResponseEntity.ok(conversion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a list of all conversion records.
     *
     * @return list of all Conversion objects
     */
    @GetMapping
    public List<Conversion> getAllConversion(){
        return conversionService.getAllConversion();
    }

    /**
     * Delete a specific conversion record by its ID.
     *
     * @param id the ID of the conversion to delete
     * @return HTTP 204 No Content if deletion was successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConversionById(@PathVariable String id){
        conversionService.deleteConversion(id);
        return ResponseEntity.noContent().build();
    }


}
