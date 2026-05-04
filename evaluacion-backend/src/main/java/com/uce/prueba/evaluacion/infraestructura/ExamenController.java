package com.uce.prueba.evaluacion.infraestructura;

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
            crearReactivo("R001", "¿Qué significa DDD?", List.of("a) Framework", "b) Domain-Driven Design", "c) Base de datos", "d) Lenguaje de programación")),
            crearReactivo("R002", "¿Qué es un Contexto Acotado?", List.of("a) Una tabla de base de datos", "b) Un servidor web", "c) Un límite semántico dentro del dominio", "d) Una interfaz de usuario")),
            crearReactivo("R003", "¿Qué es el Lenguaje Ubicuo?", List.of("a) Lenguaje común entre negocio y técnicos", "b) Un framework de JavaScript", "c) Una base de datos NoSQL", "d) Un patrón de arquitectura")),
            crearReactivo("R004", "¿Qué es un Aggregate?", List.of("a) Una colección de objetos cualquiera", "b) Una tabla de base de datos", "c) Un servicio de aplicación", "d) Un grupo de objetos con una raíz consistente")),
            crearReactivo("R005", "¿Qué es un Value Object?", List.of("a) Un objeto con identidad propia", "b) Un objeto inmutable sin identidad propia", "c) Una clase abstracta", "d) Un servicio de dominio")),
            crearReactivo("R006", "¿Qué es la Arquitectura Hexagonal?", List.of("a) Una base de datos", "b) Un framework de frontend", "c) Una arquitectura que separa el núcleo de la lógica de negocio de los detalles externos mediante puertos y adaptadores", "d) Un lenguaje de programación")),
            crearReactivo("R007", "¿Qué es un Puerto en Arquitectura Hexagonal?", List.of("a) Una interfaz que define cómo el exterior se comunica con el dominio", "b) Una base de datos", "c) Un controlador REST", "d) Un repositorio JPA")),
            crearReactivo("R008", "¿Qué es un Adaptador en Arquitectura Hexagonal?", List.of("a) Una interfaz", "b) Un caso de uso", "c) Un evento de dominio", "d) Una implementación concreta que conecta un puerto con una tecnología externa")),
            crearReactivo("R009", "¿Qué ventaja principal tiene separar el sistema en Contextos Acotados?", List.of("a) Aumenta el acoplamiento entre módulos", "b) Permite que equipos trabajen de forma autónoma y desacoplada", "c) Hace el código más difícil de mantener", "d) Reduce el rendimiento del sistema")),
            crearReactivo("R010", "¿Cuál es el objetivo principal del Lenguaje Ubicuo?", List.of("a) Confundir a los desarrolladores", "b) Aumentar la cantidad de bugs", "c) Alinear el lenguaje del negocio con el código fuente", "d) Hacer el código más largo y complejo"))
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
            .map(r -> new Respuesta(evaluadoId, r.getReactivoId(), r.getOpcionId()))
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