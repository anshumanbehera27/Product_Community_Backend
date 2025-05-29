package com.anshuman.product_community_backend.services;

import com.anshuman.product_community_backend.response.AuthResponse;
import com.anshuman.product_community_backend.request.RegisterRequest;
import com.anshuman.product_community_backend.model.User;


public interface AuthService {
    AuthResponse registerUser(RegisterRequest registerRequest);
    AuthResponse loginUser(String email, String password);
    String logoutUser(String email);

    User getUserByEmail(String email);

    boolean isEmailRegistered(String email);

    Long getCurrentUserId();
}