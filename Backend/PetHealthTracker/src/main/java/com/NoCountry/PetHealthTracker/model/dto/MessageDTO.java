package com.NoCountry.PetHealthTracker.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    @NotBlank(message = "No puede ser vacio.")
    private String message;
}
