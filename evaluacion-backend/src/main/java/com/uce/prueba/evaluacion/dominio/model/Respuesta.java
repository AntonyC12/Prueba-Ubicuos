package com.uce.prueba.evaluacion.dominio.model;

import java.time.LocalDateTime;

public class Respuesta {
    
    private final Long evaluadoId;
    private final String reactivoId;
    private final String opcionSeleccionada;
    private final LocalDateTime respondidoEn;
    
    public Respuesta(Long evaluadoId, String reactivoId, String opcionSeleccionada) {
        if (evaluadoId == null || evaluadoId <= 0) {
            throw new IllegalArgumentException("La Respuesta debe estar vinculada a un Evaluado");
        }
        if (reactivoId == null || reactivoId.isBlank()) {
            throw new IllegalArgumentException("La Respuesta debe estar vinculada a un Reactivo");
        }
        if (opcionSeleccionada == null || opcionSeleccionada.isBlank()) {
            throw new IllegalArgumentException("La Respuesta debe tener una opción seleccionada");
        }
        this.evaluadoId = evaluadoId;
        this.reactivoId = reactivoId;
        this.opcionSeleccionada = opcionSeleccionada.toLowerCase();
        this.respondidoEn = LocalDateTime.now();
    }
    
    public Long getEvaluadoId() { return evaluadoId; }
    public String getReactivoId() { return reactivoId; }
    public String getOpcionSeleccionada() { return opcionSeleccionada; }
    public LocalDateTime getRespondidoEn() { return respondidoEn; }
}