package com.uce.prueba.acceso.infraestructura;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfig {

    // Mantiene tu encriptador de contraseñas vivo
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Le dice a Spring Security que NO bloquee tus páginas ni tu API
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Necesario para que tu registro.js pueda hacer POST
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite entrar a registro.html sin login previo
            );
            
        return http.build();
    }
}