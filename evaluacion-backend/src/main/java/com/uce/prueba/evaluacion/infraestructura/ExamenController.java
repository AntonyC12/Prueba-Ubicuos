package com.uce.prueba.evaluacion.infrestructura.controller;

import com.uce.prueba.evaluacion.aplicacion.RealizarExamen;
import com.uce.prueba.evaluacion.dominio.model.Respuesta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/evaluacion")
@CrossOrigin(origins = "*")
public class ExamenController {
    
    private final RealizarExamen realizarExamen;
    
    public ExamenController(RealizarExamen realizarExamen) {
        this.realizarExamen = realizarExamen;
    }
    
    @GetMapping("/reactivos")
    public ResponseEntity<Map<String, Object>> obtenerReactivos() {
        List<Map<String, Object>> reactivos = List.of(
            crearReactivo("R001", "¿Qué es DDD?", List.of("a", "b", "c", "d")),
            crearReactivo("R002", "¿Qué es un Contexto Acotado?", List.of("a", "b", "c", "d")),
            crearReactivo("R003", "¿Qué es Lenguaje Ubicuo?", List.of("a", "b", "c", "d")),
            crearReactivo("R004", "¿Qué es un Aggregate?", List.of("a", "b", "c", "d")),
            crearReactivo("R005", "¿Qué es un Value Object?", List.of("a", "b", "c", "d"))
        );
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("reactivos", reactivos);
        respuesta.put("total", reactivos.size());
        
        return ResponseEntity.ok(respuesta);
    }
    
    @PostMapping("/{evaluadoId}/finalizar")
    public ResponseEntity<Map<String, String>> finalizarExamen(
            @PathVariable Long evaluadoId,
            @RequestBody List<RespuestaRequestItem> respuestasData) {
        
        List<Respuesta> respuestas = respuestasData.stream()
            .map(r -> new Respuesta(r.getReactivoId(), r.getOpcionId()))
            .toList();
        
        realizarExamen.finalizarExamen(evaluadoId, respuestas);
        
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Examen recibido. El diagnostico se procesara en breve");
        respuesta.put("estado", "PROCESANDO");
        
        return ResponseEntity.accepted().body(respuesta);
    }
    
    private Map<String, Object> crearReactivo(String id, String texto, List<String> opciones) {
        Map<String, Object> reactivo = new HashMap<>();
        reactivo.put("id", id);
        reactivo.put("texto", texto);
        reactivo.put("opciones", opciones);
        return reactivo;
    }
    
    public static class RespuestaRequestItem {
        private String reactivoId;
        private String opcionId;
        
        public String getReactivoId() { return reactivoId; }
        public void setReactivoId(String reactivoId) { this.reactivoId = reactivoId; }
        public String getOpcionId() { return opcionId; }
        public void setOpcionId(String opcionId) { this.opcionId = opcionId; }
    }
}