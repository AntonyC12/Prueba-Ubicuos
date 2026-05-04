package com.uce.prueba.evaluacion.infraestructura.repository;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notas")
public class NotaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long evaluadoId;
    private String valor;
    private String nivel;
    private int puntaje;
    private int total;
    private double porcentaje;
    private LocalDateTime fechaRegistro;
    
    public NotaEntity() {}
    
    public NotaEntity(Long evaluadoId, String valor, String nivel, int puntaje, int total, double porcentaje) {
        this.evaluadoId = evaluadoId;
        this.valor = valor;
        this.nivel = nivel;
        this.puntaje = puntaje;
        this.total = total;
        this.porcentaje = porcentaje;
        this.fechaRegistro = LocalDateTime.now();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEvaluadoId() { return evaluadoId; }
    public void setEvaluadoId(Long evaluadoId) { this.evaluadoId = evaluadoId; }
    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    public double getPorcentaje() { return porcentaje; }
    public void setPorcentaje(double porcentaje) { this.porcentaje = porcentaje; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}