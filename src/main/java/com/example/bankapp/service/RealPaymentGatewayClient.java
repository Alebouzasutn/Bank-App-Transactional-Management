package com.example.bankapp.service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RealPaymentGatewayClient implements PaymentGateway {

    private final WebClient webClient;

    public RealPaymentGatewayClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.real-gateway.com").build();
    }

    @Override
    public boolean processPayment(String cardNumber, double amount) {
        try {
            String response = webClient.post()
                    .uri("/payments")
                    .bodyValue(new PaymentRequest(cardNumber, amount))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return "APPROVED".equalsIgnoreCase(response);
        } catch (Exception e) {
            return false;
        }
    }

    // DTO interno
    private record PaymentRequest(String cardNumber, double amount) {}
}

