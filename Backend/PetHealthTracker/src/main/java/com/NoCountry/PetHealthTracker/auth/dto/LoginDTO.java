package com.NoCountry.PetHealthTracker.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginDTO {

    @NotBlank(message = "el correo no puede ser vacio")
    @Email(message = "debe tener formato de correo")
    private String email;

    @NotBlank(message = "La contraseña no puede ser vacia.")
    @Size(min = 8 , message = "La contraseña debe tener minimo 8 caracteres")
    private String password;

    // Getters
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
