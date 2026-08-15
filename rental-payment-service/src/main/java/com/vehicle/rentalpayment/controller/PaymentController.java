package com.vehicle.rentalpayment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.rentalpayment.dto.PaymentRequest;
import com.vehicle.rentalpayment.model.Payment;
import com.vehicle.rentalpayment.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment Management", description = "Payment related endpoints")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    @Operation(summary = "Proceed a Payment")
    public ResponseEntity<Payment> processPayment(@RequestBody PaymentRequest request) {
        Payment payment = paymentService.processPayment(request);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @GetMapping("/history/{userId}")
    @Operation(summary = "Payment history of a user")
    public ResponseEntity<List<Payment>> getPaymentHistory(@PathVariable String userId) {
        List<Payment> history = paymentService.getPaymentHistory(userId);
        return ResponseEntity.ok(history);
    }
}