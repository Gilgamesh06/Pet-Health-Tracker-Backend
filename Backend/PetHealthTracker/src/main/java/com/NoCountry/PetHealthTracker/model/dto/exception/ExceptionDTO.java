package com.NoCountry.PetHealthTracker.model.dto.exception;

import java.time.LocalDateTime;

public record ExceptionDTO(
        int statusCode,
        String message,
        LocalDateTime date
){}
