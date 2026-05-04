package com.uce.prueba.evaluacion.aplicacion;

import com.uce.prueba.evaluacion.dominio.eventos.ExamenFinalizado;
import com.uce.prueba.evaluacion.dominio.model.Respuesta;
import com.uce.prueba.evaluacion.dominio.repository.NotaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RealizarExamen {
    
    private final ApplicationEventPublisher eventPublisher;
    private final NotaRepository notaRepository;
    
    public RealizarExamen(ApplicationEventPublisher eventPublisher, NotaRepository notaRepository) {
        this.eventPublisher = eventPublisher;
        this.notaRepository = notaRepository;
    }
    
    public void finalizarExamen(Long evaluadoId, List<Respuesta> respuestas) {
        // Regla de Negocio: Unicidad de Evaluación
        if (notaRepository.buscarPorEvaluadoId(evaluadoId).isPresent()) {
            throw new IllegalStateException("El Evaluado ya tiene un Diagnóstico finalizado en la sesión actual.");
        }

        ExamenFinalizado evento = new ExamenFinalizado(evaluadoId, respuestas);
        eventPublisher.publishEvent(evento);
        System.out.println("Evento ExamenFinalizado publicado para Evaluado: " + evaluadoId);
    }
}