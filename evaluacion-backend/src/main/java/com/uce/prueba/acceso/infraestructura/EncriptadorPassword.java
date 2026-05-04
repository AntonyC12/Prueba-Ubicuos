package com.uce.prueba.acceso.infraestructura;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncriptadorPassword {

    private final PasswordEncoder passwordEncoder;

    public EncriptadorPassword() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Cifra la contraseña del usuario.
     * @param rawPassword Contraseña en texto plano.
     * @return Contraseña encriptada.
     */
    public String encriptar(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Verifica si la contraseña en texto plano coincide con el hash almacenado.
     * @param rawPassword Contraseña en texto plano.
     * @param encodedPassword Contraseña encriptada de la base de datos.
     * @return true si coinciden, false en caso contrario.
     */
    public boolean verificar(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}