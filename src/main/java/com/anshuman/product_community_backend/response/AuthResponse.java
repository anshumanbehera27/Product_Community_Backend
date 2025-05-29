package com.anshuman.product_community_backend.response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthResponse {
    private String token;
    private String email;
    private Long userId;

    public AuthResponse(String token, String email, Long userId) {
        this.token = token;
        this.email = email;
        this.userId = userId;
    }


}
