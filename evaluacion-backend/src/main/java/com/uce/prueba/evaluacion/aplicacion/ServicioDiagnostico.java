package com.uce.prueba.evaluacion.aplicacion;

import com.uce.prueba.evaluacion.dominio.eventos.ExamenFinalizado;
import com.uce.prueba.evaluacion.dominio.model.NivelComprension;
import com.uce.prueba.evaluacion.dominio.model.Nota;
import com.uce.prueba.evaluacion.dominio.model.Reactivo;
import com.uce.prueba.evaluacion.dominio.model.Respuesta;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServicioDiagnostico {
    
    private Map<String, Reactivo> obtenerReactivos() {
        Map<String, Reactivo> reactivos = new HashMap<>();
        reactivos.put("R001", new Reactivo("R001", "¿Qué es DDD?", "b"));
        reactivos.put("R002", new Reactivo("R002", "¿Qué es un Contexto Acotado?", "c"));
        reactivos.put("R003", new Reactivo("R003", "¿Qué es Lenguaje Ubicuo?", "a"));
        reactivos.put("R004", new Reactivo("R004", "¿Qué es un Aggregate?", "d"));
        reactivos.put("R005", new Reactivo("R005", "¿Qué es un Value Object?", "b"));
        return reactivos;
    }
    
    @EventListener
    public Nota procesarDiagnostico(ExamenFinalizado evento) {
        Map<String, Reactivo> reactivos = obtenerReactivos();
        int aciertos = 0;
        int total = evento.getRespuestas().size();
        
        for (Respuesta respuesta : evento.getRespuestas()) {
            Reactivo reactivo = reactivos.get(respuesta.getReactivoId());
            if (reactivo != null && reactivo.esCorrecta(respuesta.getOpcionSeleccionada())) {
                aciertos++;
            }
        }
        
        double porcentaje = (double) aciertos / total * 100;
        NivelComprension nivel = NivelComprension.fromPorcentaje(porcentaje);
        String nota = nivel.getNota();
        
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("DIAGNOSTICO COMPLETADO");
        System.out.println("Evaluado ID: " + evento.getEvaluadoId());
        System.out.println("Aciertos: " + aciertos + "/" + total);
        System.out.println("Porcentaje: " + String.format("%.1f", porcentaje) + "%");
        System.out.println("Nivel: " + nivel.name());
        System.out.println("Nota: " + nota);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        return new Nota(nivel, nota, aciertos, total);
    }
}