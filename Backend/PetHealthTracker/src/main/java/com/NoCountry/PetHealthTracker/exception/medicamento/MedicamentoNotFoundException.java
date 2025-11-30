package com.NoCountry.PetHealthTracker.exception.medicamento;

public class MedicamentoNotFoundException extends RuntimeException {

    public MedicamentoNotFoundException(String message){
        super(message);
    }
}
