package com.example.bankapp.controller;

import com.example.bankapp.dto.TokenResponse;
import com.example.bankapp.entity.User;
import com.example.bankapp.repository.UserRepository;
import com.example.bankapp.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody User u) {
        User found = userRepo.findByUsername(u.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid"));
        if (!encoder.matches(u.getPassword(), found.getPassword())) throw new IllegalArgumentException("Invalid");
        String token = jwtProvider.createToken(found.getUsername(), found.getRoles());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
