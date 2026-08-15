package com.vehicle.rentalpayment.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

    @Id
    private String id;

    private String userId;
    private String vehicleId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private double totalAmount;

    // PENDING, CONFIRMED, ONGOING, COMPLETED, CANCELLED
    private String status;

    private LocalDateTime createdAt = LocalDateTime.now();
}