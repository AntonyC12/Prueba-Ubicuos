package com.uce.prueba.evaluacion.aplicacion;

import com.uce.prueba.evaluacion.dominio.eventos.ExamenFinalizado;
import com.uce.prueba.evaluacion.dominio.model.Nota;
import com.uce.prueba.evaluacion.dominio.model.Reactivo;
import com.uce.prueba.evaluacion.dominio.model.Respuesta;
import com.uce.prueba.evaluacion.dominio.model.Resultado;
import com.uce.prueba.evaluacion.dominio.repository.NotaRepository;
import com.uce.prueba.evaluacion.dominio.service.DiagnosticoDomainService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServicioDiagnostico {
    
    private final NotaRepository notaRepository;
    private final DiagnosticoDomainService diagnosticoDomainService;
    
    public ServicioDiagnostico(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
        this.diagnosticoDomainService = new DiagnosticoDomainService();
    }
    
    private Map<String, Reactivo> obtenerReactivos() {
        Map<String, Reactivo> reactivos = new HashMap<>();
        // En un escenario real, esto vendría de un ReactivoRepository
        reactivos.put("R001", new Reactivo("R001", "¿Qué significa DDD?", "b"));
        reactivos.put("R002", new Reactivo("R002", "¿Qué es un Contexto Acotado?", "c"));
        reactivos.put("R003", new Reactivo("R003", "¿Qué es el Lenguaje Ubicuo?", "a"));
        reactivos.put("R004", new Reactivo("R004", "¿Qué es un Aggregate?", "d"));
        reactivos.put("R005", new Reactivo("R005", "¿Qué es un Value Object?", "b"));
        reactivos.put("R006", new Reactivo("R006", "¿Qué es la Arquitectura Hexagonal?", "c"));
        reactivos.put("R007", new Reactivo("R007", "¿Qué es un Puerto en Hexagonal?", "a"));
        reactivos.put("R008", new Reactivo("R008", "¿Qué es un Adaptador en Hexagonal?", "d"));
        reactivos.put("R009", new Reactivo("R009", "¿Qué ventaja tiene separar por Contextos Acotados?", "b"));
        reactivos.put("R010", new Reactivo("R010", "¿Cuál es el objetivo del Lenguaje Ubicuo?", "c"));
        return reactivos;
    }
    
    @EventListener
    public void procesarDiagnostico(ExamenFinalizado evento) {
        Map<String, Reactivo> reactivosMap = obtenerReactivos();
        int aciertos = 0;
        int total = evento.getRespuestas().size();
        
        System.out.println("📊 PROCESANDO DIAGNÓSTICO PARA EVALUADO: " + evento.getEvaluadoId());
        
        for (Respuesta respuesta : evento.getRespuestas()) {
            Reactivo reactivo = reactivosMap.get(respuesta.getReactivoId());
            if (reactivo != null && reactivo.esCorrecta(respuesta.getOpcionSeleccionada())) {
                aciertos++;
            }
        }
        
        // Uso del Lenguaje Ubicuo: Resultado -> DiagnosticoDomainService -> Nota
        Resultado resultado = new Resultado(aciertos, total);
        Nota notaCalculada = diagnosticoDomainService.calcularDiagnostico(resultado);
        
        // Persistencia del Diagnóstico (Invariante: Inmutabilidad)
        notaRepository.guardar(evento.getEvaluadoId(), notaCalculada);
        
        System.out.println("✅ DIAGNÓSTICO FINALIZADO");
        System.out.println("   Resultado: " + resultado);
        System.out.println("   Nota: " + notaCalculada.getValor());
        System.out.println("   Nivel: " + notaCalculada.getNivel());
    }
}