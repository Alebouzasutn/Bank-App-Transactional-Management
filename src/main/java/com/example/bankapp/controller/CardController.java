package com.example.bankapp.controller;

import com.example.bankapp.dto.CardRegisterDTO;
import com.example.bankapp.dto.CardResponseDTO;
import com.example.bankapp.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/cards")
    @RequiredArgsConstructor
    public class CardController {

        private final CardService cardService;

        @PostMapping("/register")
        public CardResponseDTO registerCard(@Valid @RequestBody CardRegisterDTO dto,
                                            Authentication auth) {
            String username = auth.getName();
            return cardService.registerCard(username, dto);
        }

        @GetMapping
        public List<CardResponseDTO> getUserCards(Authentication auth) {
            String username = auth.getName();
            return cardService.getCardsByUser(username);
        }
    }

