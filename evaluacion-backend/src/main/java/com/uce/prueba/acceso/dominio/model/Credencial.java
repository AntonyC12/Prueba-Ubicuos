package com.uce.prueba.acceso.dominio.model;

import java.util.Objects;

public final class Credencial {

    private final String email;
    private final String password;

    public Credencial(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credencial that = (Credencial) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        // Enmascaramos la contraseña por buenas prácticas de seguridad y logs
        return "Credencial{" +
                "email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}