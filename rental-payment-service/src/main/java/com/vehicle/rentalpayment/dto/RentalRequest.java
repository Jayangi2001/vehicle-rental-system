package com.vehicle.rentalpayment.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RentalRequest {
    private String userId;
    private String vehicleId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double totalAmount;
}