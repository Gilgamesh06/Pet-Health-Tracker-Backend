package com.NoCountry.PetHealthTracker.service.utility;

public class StringUtils {

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}