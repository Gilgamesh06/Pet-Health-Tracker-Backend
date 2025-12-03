package com.NoCountry.PetHealthTracker.exception.user;

public class PasswordEqualsException extends RuntimeException{

    public PasswordEqualsException(String message){
        super(message);
    }
}
