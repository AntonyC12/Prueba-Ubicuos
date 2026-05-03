package com.uce.prueba.evaluacion.dominio.eventos;

import com.uce.prueba.evaluacion.dominio.model.Respuesta;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExamenFinalizado {
    
    private final Long evaluadoId;
    private final List<Respuesta> respuestas;
    private final LocalDateTime fechaFinalizacion;
    
    public ExamenFinalizado(Long evaluadoId, List<Respuesta> respuestas) {
        if (evaluadoId == null || evaluadoId <= 0) {
            throw new IllegalArgumentException("El ID del evaluado es obligatorio");
        }
        if (respuestas == null || respuestas.isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos una respuesta");
        }
        this.evaluadoId = evaluadoId;
        this.respuestas = Collections.unmodifiableList(new ArrayList<>(respuestas));
        this.fechaFinalizacion = LocalDateTime.now();
    }
    
    public Long getEvaluadoId() { return evaluadoId; }
    public List<Respuesta> getRespuestas() { return respuestas; }
    public LocalDateTime getFechaFinalizacion() { return fechaFinalizacion; }
}