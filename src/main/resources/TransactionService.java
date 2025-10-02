package com.example.bankapp.service;

import com.example.bankapp.entity.*;
import com.example.bankapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final CardRepository cardRepo;
    private final TransactionRepository txRepo;
    private final PaymentGatewayClient gateway;

    @Transactional
    public Transaction processTransaction(String cardToken, BigDecimal amount, String merchant) {
        Card card = cardRepo.findByToken(cardToken)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        if (card.getStatus() != CardStatus.ACTIVE) throw new IllegalStateException("Card not active");
        if (isExpired(card)) throw new IllegalStateException("Card expired");

        if (card.getAvailableLimit().compareTo(amount) < 0)
            throw new IllegalStateException("Insufficient available limit");

        Transaction tx = Transaction.builder()
                .card(card)
                .amount(amount)
                .currency("ARS")
                .merchant(merchant)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        tx = txRepo.save(tx);

        PaymentResult result = gateway.charge(card.getToken(), amount, merchant);

        if (result.approved()) {
            card.setAvailableLimit(card.getAvailableLimit().subtract(amount));
            cardRepo.save(card);

            tx.setStatus(TransactionStatus.APPROVED);
            tx.setExternalReference(result.externalId());
            tx.setProcessedAt(LocalDateTime.now());
            return txRepo.save(tx);
        } else {
            tx.setStatus(TransactionStatus.DECLINED);
            tx.setExternalReference(result.externalId());
            tx.setProcessedAt(LocalDateTime.now());
            return txRepo.save(tx);
        }
    }

    private boolean isExpired(Card c) {
        YearMonth expiry = YearMonth.of(c.getExpiryYear(), c.getExpiryMonth());
        return expiry.isBefore(YearMonth.now());
    }
}
