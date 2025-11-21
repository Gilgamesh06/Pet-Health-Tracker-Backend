package com.NoCountry.PetHealthTracker.service.interfaces;

public interface UpdateProcess<T, D> {
    T update(T object, D dto);
}
