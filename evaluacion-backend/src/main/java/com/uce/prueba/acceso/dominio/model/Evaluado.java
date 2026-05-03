package com.uce.prueba.acceso.dominio.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Evaluado {
    
    private Long id;
    private String nombre;
    private String email;
    private LocalDateTime fechaRegistro;

    // Constructor vacío requerido por ciertos frameworks o para serialización
    public Evaluado() {
    }

    // Constructor principal para inicializar la entidad
    public Evaluado(Long id, String nombre, String email, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    // Métodos equals y hashCode basados en el identificador único (ID)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evaluado evaluado = (Evaluado) o;
        return Objects.equals(id, evaluado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Método toString para facilitar el debugging y registro en consola
    @Override
    public String toString() {
        return "Evaluado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}