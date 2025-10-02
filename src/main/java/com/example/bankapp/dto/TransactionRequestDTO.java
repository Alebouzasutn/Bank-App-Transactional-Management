package com.example.bankapp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {
    private String cardToken;
    private BigDecimal amount;
    private String merchant;
}
