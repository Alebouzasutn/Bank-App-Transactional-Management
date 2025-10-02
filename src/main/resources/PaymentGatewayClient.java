package com.example.bankapp.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class PaymentGatewayClient {

    public PaymentResult charge(String cardToken, BigDecimal amount, String merchant) {
        // Mock behavior: approve if amount < 10000, else decline.
        boolean approved = amount.compareTo(new java.math.BigDecimal("10000")) < 0;
        return new PaymentResult(approved, UUID.randomUUID().toString());
    }
}
