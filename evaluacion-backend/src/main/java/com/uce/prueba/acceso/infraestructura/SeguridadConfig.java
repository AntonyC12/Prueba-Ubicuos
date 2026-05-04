package com.uce.prueba.acceso.infraestructura;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfig {

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Solo desarrollo, evita errores al usar POST desde JS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll()  // Tus endpoints REST
                .requestMatchers("/**").permitAll()      // Tu UI (html/js/css)
            );
        return http.build();
    }
}