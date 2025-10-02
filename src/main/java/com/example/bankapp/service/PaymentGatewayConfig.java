package com.example.bankapp.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
    public class PaymentGatewayConfig {

        @Bean
        @Profile("dev")
        public PaymentGateway mockPaymentGateway() {
            return new MockPaymentGatewayClient();
        }

        @Bean
        @Profile("prod")
        public PaymentGateway realPaymentGateway(WebClient.Builder builder) {
            return new RealPaymentGatewayClient(builder);
        }
    }


