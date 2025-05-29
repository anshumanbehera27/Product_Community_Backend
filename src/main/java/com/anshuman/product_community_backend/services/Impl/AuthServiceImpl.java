package com.anshuman.product_community_backend.services.Impl;

import com.anshuman.product_community_backend.response.AuthResponse;
import com.anshuman.product_community_backend.request.RegisterRequest;
import com.anshuman.product_community_backend.model.User;
import com.anshuman.product_community_backend.utils.JwtUtil;
import com.anshuman.product_community_backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.anshuman.product_community_backend.repository.userRepository;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    // make it in util class

    @Override
    public AuthResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getFirstName(), savedUser.getLastName());
        return new AuthResponse(token, savedUser.getEmail(), savedUser.getId());

    }

    @Override
    public AuthResponse loginUser(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail(), user.getFirstName(), user.getLastName());

            // Return authentication response with token and user details
            return new AuthResponse(token, user.getEmail(), user.getId());

        } catch (Exception e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage(), e);

        }
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public String logoutUser(String email) {
        return "";
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return false;
    }

    @Override
    public Long getCurrentUserId() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + currentUserEmail));
        return user.getId();
    }
}
