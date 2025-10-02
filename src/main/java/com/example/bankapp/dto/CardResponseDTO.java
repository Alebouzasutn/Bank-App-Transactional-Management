package com.example.bankapp.dto;

import lombok.Data;

public class CardResponseDTO {

    private Long id;
    private String cardNumberMasked;
    private String cardHolderName;
    private Integer expirationMonth;
    private Integer expirationYear;

}
