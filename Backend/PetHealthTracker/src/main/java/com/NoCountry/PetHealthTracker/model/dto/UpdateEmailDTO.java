package com.NoCountry.PetHealthTracker.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateEmailDTO {

    @NotBlank(message = "el email no puede ser vacio.")
    @Email(message = "el email debe tener formato correcto.")
    private String email;

    public UpdateEmailDTO(){}

    public UpdateEmailDTO(String email){
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
