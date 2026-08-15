package com.vehicle.rentalpayment.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    private String id;

    private String rentalId;
    private String userId;

    private double amount;

    // CARD, CASH, ONLINE
    private String paymentMethod;

    // SUCCESS, FAILED, PENDING
    private String paymentStatus;

    private LocalDateTime paymentDate = LocalDateTime.now();
}