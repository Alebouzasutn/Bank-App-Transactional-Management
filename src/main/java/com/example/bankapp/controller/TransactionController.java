package com.example.bankapp.controller;

import com.example.bankapp.dto.TransactionRequestDTO;
import com.example.bankapp.entity.Transaction;
import com.example.bankapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService txService;

    @PostMapping
    public ResponseEntity<Transaction> process(@RequestBody TransactionRequestDTO dto) {
        Transaction tx = txService.processTransaction(dto.getCardToken(), dto.getAmount(), dto.getMerchant());
        return ResponseEntity.ok(tx);
    }
}
