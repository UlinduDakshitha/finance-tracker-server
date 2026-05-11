package com.financetracker.backend.controller;

import com.financetracker.backend.dto.response.AuthResponse;
import com.financetracker.backend.dto.response.AuthResponse;
import com.financetracker.backend.dto.request.LoginRequest;
import com.financetracker.backend.dto.request.RegisterRequest;
import com.financetracker.backend.entity.User;
import com.financetracker.backend.repository.UserRepository;
import com.financetracker.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(new Object() {
            public final Long id = user.getId();
            public final String name = user.getName();
            public final String userEmail = user.getEmail();
        });
    }
}
