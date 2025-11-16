package com.NoCountry.PetHealthTracker.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class TokenDTO {

    @NotBlank(message = "No puede estar vacio.")
    private String accessToken;

    @NotBlank(message = "No puede estar vacio.")
    private String refreshToken;

    public TokenDTO(){}

    public TokenDTO(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Setters
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Getters
    public String getAccessToken() {
        return accessToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
}
