package com.example.bankapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

    @Data
    public class CardRegisterDTO {
        @NotBlank
        private String cardNumber;

        @NotBlank
        private String cardHolderName;

        @NotNull
        private Integer expirationMonth;

        @NotNull
        private Integer expirationYear;
    }

