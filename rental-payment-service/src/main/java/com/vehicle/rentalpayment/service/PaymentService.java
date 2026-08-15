package com.vehicle.rentalpayment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicle.rentalpayment.dto.PaymentRequest;
import com.vehicle.rentalpayment.model.Payment;
import com.vehicle.rentalpayment.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setRentalId(request.getRentalId());
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());

        payment.setPaymentStatus("SUCCESS");

        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentHistory(String userId) {
        return paymentRepository.findByUserId(userId);
    }
}