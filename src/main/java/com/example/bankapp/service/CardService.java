package com.example.bankapp.service;

import com.example.bankapp.dto.CardRegisterDTO;
import com.example.bankapp.dto.CardResponseDTO;
import com.example.bankapp.entity.Card;
import com.example.bankapp.entity.User;
import com.example.bankapp.repository.CardRepository;
import com.example.bankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardResponseDTO registerCard(String username, CardRegisterDTO dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Card card = new Card();
        card.setCardNumber(dto.getCardNumber());
        card.setCardHolderName(dto.getCardHolderName());
        card.setExpirationMonth(dto.getExpirationMonth());
        card.setExpirationYear(dto.getExpirationYear());
        card.setUser(user);

        Card saved = cardRepository.save(card);
        return mapToResponse(saved);
    }

    public List<CardResponseDTO> getCardsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cardRepository.findByUser(user).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CardResponseDTO mapToResponse(Card card) {
        CardResponseDTO dto = new CardResponseDTO();
        dto.setId(card.getId());
        dto.setCardHolderName(card.getCardHolderName());
        dto.setExpirationMonth(card.getExpirationMonth());
        dto.setExpirationYear(card.getExpirationYear());
        dto.setCardNumberMasked(maskCardNumber(card.getCardNumber()));
        return dto;
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() < 4) return "****";
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}

