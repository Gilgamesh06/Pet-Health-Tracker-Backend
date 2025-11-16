package com.NoCountry.PetHealthTracker.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class TokenRefreshRequest {

    public TokenRefreshRequest(){}

    @NotBlank(message = "el token no puede ser vacio")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
