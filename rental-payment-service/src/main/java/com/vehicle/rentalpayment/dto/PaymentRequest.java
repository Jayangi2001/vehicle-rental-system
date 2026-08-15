package com.vehicle.rentalpayment.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String rentalId;
    private String userId;
    private double amount;
    private String paymentMethod;
}