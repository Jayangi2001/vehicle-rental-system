package com.vehicle.rentalpayment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.rentalpayment.dto.RentalRequest;
import com.vehicle.rentalpayment.model.Rental;
import com.vehicle.rentalpayment.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/rentals")
@Tag(name = "Rental Management", description = "Vehicle rental related endpoints")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping
    @Operation(summary = "Rent a vehicle")
    public ResponseEntity<Rental> createRental(@RequestBody RentalRequest request) {
        Rental rental = rentalService.createRental(request);
        return new ResponseEntity<>(rental, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Rental Details")
    public ResponseEntity<Rental> getRentalById(@PathVariable String id) {
        return rentalService.getRentalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}