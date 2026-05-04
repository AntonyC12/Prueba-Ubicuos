package com.uce.prueba.evaluacion.aplicacion;

import com.uce.prueba.evaluacion.dominio.eventos.ExamenFinalizado;
import com.uce.prueba.evaluacion.dominio.model.Respuesta;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RealizarExamen {
    
    private final ApplicationEventPublisher eventPublisher;
    
    public RealizarExamen(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
    public void finalizarExamen(Long evaluadoId, List<Respuesta> respuestas) {
        ExamenFinalizado evento = new ExamenFinalizado(evaluadoId, respuestas);
        eventPublisher.publishEvent(evento);
        System.out.println("Evento ExamenFinalizado publicado para Evaluado: " + evaluadoId);
    }
}