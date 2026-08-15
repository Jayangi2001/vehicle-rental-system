package com.vehicle.rentalpayment.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicle.rentalpayment.dto.RentalRequest;
import com.vehicle.rentalpayment.model.Rental;
import com.vehicle.rentalpayment.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public Rental createRental(RentalRequest request) {
        Rental rental = new Rental();
        rental.setUserId(request.getUserId());
        rental.setVehicleId(request.getVehicleId());
        rental.setStartDate(request.getStartDate());
        rental.setEndDate(request.getEndDate());
        rental.setTotalAmount(request.getTotalAmount());
        rental.setStatus("PENDING");

        return rentalRepository.save(rental);
    }

    public Optional<Rental> getRentalById(String id) {
        return rentalRepository.findById(id);
    }
}