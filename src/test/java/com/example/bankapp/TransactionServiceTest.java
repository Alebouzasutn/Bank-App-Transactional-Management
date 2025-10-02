package com.example.bankapp;

import com.example.bankapp.entity.*;
import com.example.bankapp.repository.*;
import com.example.bankapp.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionServiceTest {

    @Test
    void processTransaction_approved_updatesAvailableLimit() {
        CardRepository cardRepo = Mockito.mock(CardRepository.class);
        TransactionRepository txRepo = Mockito.mock(TransactionRepository.class);
        PaymentGatewayClient gateway = Mockito.mock(PaymentGatewayClient.class);

        User user = User.builder().id(1L).username("u").password("p").build();
        Card card = Card.builder()
                .id(1L)
                .user(user)
                .token("tok_1")
                .availableLimit(new BigDecimal("10000"))
                .expiryMonth(12)
                .expiryYear(2099)
                .status(CardStatus.ACTIVE)
                .build();

        Mockito.when(cardRepo.findByToken("tok_1")).thenReturn(Optional.of(card));
        Mockito.when(txRepo.save(Mockito.any(Transaction.class))).thenAnswer(i -> i.getArgument(0));
        Mockito.when(gateway.charge(Mockito.eq("tok_1"), Mockito.any(BigDecimal.class), Mockito.anyString()))
                .thenReturn(new PaymentResult(true, "ext123"));

        TransactionService service = new TransactionService(cardRepo, txRepo, gateway);

        Transaction tx = service.processTransaction("tok_1", new BigDecimal("1000"), "MERCHANT");
        assertEquals(TransactionStatus.APPROVED, tx.getStatus());
        assertEquals(new BigDecimal("9000"), card.getAvailableLimit());
    }

    @Test
    void processTransaction_declined_doesNotChangeAvailable() {
        CardRepository cardRepo = Mockito.mock(CardRepository.class);
        TransactionRepository txRepo = Mockito.mock(TransactionRepository.class);
        PaymentGatewayClient gateway = Mockito.mock(PaymentGatewayClient.class);

        User user = User.builder().id(1L).username("u").password("p").build();
        Card card = Card.builder()
                .id(1L)
                .user(user)
                .token("tok_1")
                .availableLimit(new BigDecimal("500"))
                .expiryMonth(12)
                .expiryYear(2099)
                .status(CardStatus.ACTIVE)
                .build();

        Mockito.when(cardRepo.findByToken("tok_1")).thenReturn(Optional.of(card));
        Mockito.when(txRepo.save(Mockito.any(Transaction.class))).thenAnswer(i -> i.getArgument(0));
        Mockito.when(gateway.charge(Mockito.eq("tok_1"), Mockito.any(BigDecimal.class), Mockito.anyString()))
                .thenReturn(new PaymentResult(false, "ext999"));

        TransactionService service = new TransactionService(cardRepo, txRepo, gateway);

        Transaction tx = service.processTransaction("tok_1", new BigDecimal("600"), "MERCHANT");
        assertEquals(TransactionStatus.DECLINED, tx.getStatus());
        assertEquals(new BigDecimal("500"), card.getAvailableLimit());
    }
}
