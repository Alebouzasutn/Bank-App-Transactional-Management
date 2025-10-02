package com.example.bankapp.service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PaymentGatewayClient {

    private final WebClient webClient;

    public PaymentGatewayClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.fake-gateway.com") // aquí va la URL del gateway real o mock
                .build();
    }

    /**
     * Simula el procesamiento de un pago.
     * @param cardNumber número de tarjeta
     * @param amount monto a debitar
     * @return true si fue aprobado, false si rechazado
     */
    public boolean processPayment(String cardNumber, double amount) {
        try {
            // Request ficticio al gateway
            String response = webClient.post()
                    .uri("/payments")
                    .bodyValue(new PaymentRequest(cardNumber, amount))
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(e -> Mono.just("DECLINED"))
                    .block();

            return "APPROVED".equalsIgnoreCase(response);
        } catch (Exception e) {
            return false;
        }
    }

    // DTO interno para request
    private record PaymentRequest(String cardNumber, double amount) {}
}
