package com.anshuman.product_community_backend.controller;


import com.anshuman.product_community_backend.response.AuthResponse;
import com.anshuman.product_community_backend.request.LoginRequest;
import com.anshuman.product_community_backend.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.anshuman.product_community_backend.services.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registrationRequest) {
        return ResponseEntity.ok(authService.registerUser(registrationRequest));

    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.loginUser(loginRequest.getEmail(),loginRequest.getPassword()));
    }




}
