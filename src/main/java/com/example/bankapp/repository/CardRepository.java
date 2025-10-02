package com.example.bankapp.repository;

import com.example.bankapp.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByToken(String token);

    @Lock(LockModeType.OPTIMISTIC)
    Optional<Card> findById(Long id);
}
