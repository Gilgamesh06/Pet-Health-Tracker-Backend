package com.NoCountry.PetHealthTracker.config.encrypt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncryptConfig {

    /**
     * Metodo que retorna el PasswordEnceoder
     * @return PassworEncoder implementacion BCrypt -> metodos encode y matches
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
