package com.example.bankapp.service;

import org.springframework.stereotype.Component;

    @Component
    public class MockPaymentGatewayClient implements PaymentGateway {

        @Override
        public boolean processPayment(String cardNumber, double amount) {
            // Lógica ficticia simple
            return amount <= 1000;
        }
    }


