package com.example.bankapp.service;

import com.example.bankapp.entity.Card;
import com.example.bankapp.entity.Transaction;
import com.example.bankapp.repository.CardRepository;
import com.example.bankapp.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final PaymentGateway paymentGateway; // ahora usamos la interfaz

    public TransactionService(TransactionRepository transactionRepository,
                              CardRepository cardRepository,
                              PaymentGateway paymentGateway) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.paymentGateway = paymentGateway;
    }

    public Transaction createTransaction(Long cardId, double amount) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (card.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        boolean approved = paymentGateway.processPayment(card.getNumber(), amount);

        if (!approved) {
            throw new RuntimeException("Payment declined");
        }

        card.setBalance(card.getBalance() - amount);
        Transaction tx = new Transaction();
        tx.setCard(card);
        tx.setAmount(amount);
        tx.setStatus("APPROVED");

        transactionRepository.save(tx);
        return tx;
    }
}

