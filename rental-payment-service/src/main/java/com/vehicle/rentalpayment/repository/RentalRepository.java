package com.vehicle.rentalpayment.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vehicle.rentalpayment.model.Rental;

@Repository
public interface RentalRepository extends MongoRepository<Rental, String> {

    List<Rental> findByUserId(String userId);

    List<Rental> findByVehicleId(String vehicleId);
}