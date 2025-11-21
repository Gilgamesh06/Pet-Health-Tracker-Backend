package com.NoCountry.PetHealthTracker.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordDTO {

    @NotBlank(message = "La constraseña no puede ser vacia.")
    @Size(min = 8, message = "La contraseña no puede ser menor de 8 caracteres")
    private String password;

    public UpdatePasswordDTO(){}

    public UpdatePasswordDTO(String password){
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
