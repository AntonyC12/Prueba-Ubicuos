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
        
        // 10 Reactivos sobre DDD y Arquitectura Hexagonal
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
    
    private NivelComprension determinarNivelComprension(double porcentaje) {
        if (porcentaje >= 70) return NivelComprension.AVANZADO;
        if (porcentaje >= 50) return NivelComprension.INTERMEDIO;
        return NivelComprension.BASICO;
    }
    
    @EventListener
    public Nota procesarDiagnostico(ExamenFinalizado evento) {
        Map<String, Reactivo> reactivos = obtenerReactivos();
        int aciertos = 0;
        int total = evento.getRespuestas().size();
        
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📊 DIAGNOSTICO - EVALUACION DDD");
        System.out.println("Evaluado ID: " + evento.getEvaluadoId());
        System.out.println("Total Reactivos: " + total);
        
        for (Respuesta respuesta : evento.getRespuestas()) {
            Reactivo reactivo = reactivos.get(respuesta.getReactivoId());
            if (reactivo != null && reactivo.esCorrecta(respuesta.getOpcionSeleccionada())) {
                aciertos++;
                System.out.println("   ✅ Correcto: " + reactivo.getTexto());
            } else {
                System.out.println("   ❌ Incorrecto: " + 
                    (reactivo != null ? reactivo.getTexto() : "Reactivo no encontrado"));
            }
        }
        
        double porcentaje = (double) aciertos / total * 100;
        NivelComprension nivel = determinarNivelComprension(porcentaje);
        String nota = nivel.getNota();
        
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("RESULTADO FINAL");
        System.out.println("Aciertos: " + aciertos + "/" + total);
        System.out.println("Porcentaje: " + String.format("%.1f", porcentaje) + "%");
        System.out.println("Nivel de Comprensión: " + nivel.name());
        System.out.println("Nota: " + nota);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        return new Nota(nivel, nota, aciertos, total);
    }
}