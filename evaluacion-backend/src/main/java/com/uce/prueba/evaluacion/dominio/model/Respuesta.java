package com.uce.prueba.evaluacion.dominio.model;

import java.time.LocalDateTime;

public class Respuesta {
    
    private final String reactivoId;
    private final String opcionSeleccionada;
    private final LocalDateTime respondidoEn;
    
    public Respuesta(String reactivoId, String opcionSeleccionada) {
        if (reactivoId == null || reactivoId.isBlank()) {
            throw new IllegalArgumentException("La Respuesta debe estar vinculada a un Reactivo");
        }
        if (opcionSeleccionada == null || opcionSeleccionada.isBlank()) {
            throw new IllegalArgumentException("La Respuesta debe tener una opción seleccionada");
        }
        this.reactivoId = reactivoId;
        this.opcionSeleccionada = opcionSeleccionada.toLowerCase();
        this.respondidoEn = LocalDateTime.now();
    }
    
    public String getReactivoId() { return reactivoId; }
    public String getOpcionSeleccionada() { return opcionSeleccionada; }
    public LocalDateTime getRespondidoEn() { return respondidoEn; }
}