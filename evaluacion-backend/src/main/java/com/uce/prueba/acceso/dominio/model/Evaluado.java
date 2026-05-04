package com.uce.prueba.acceso.dominio.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Evaluado {
    
    private Long id;
    private String nombre;
    private Credencial credencial; // Agrupa email y password (hash)
    private LocalDateTime fechaRegistro;

    public Evaluado() {
    }

    public Evaluado(Long id, String nombre, Credencial credencial, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.credencial = credencial;
        this.fechaRegistro = fechaRegistro;
    }

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

    public Credencial getCredencial() {
        return credencial;
    }

    public void setCredencial(Credencial credencial) {
        this.credencial = credencial;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

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

    @Override
    public String toString() {
        return "Evaluado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", credencial=" + credencial +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}