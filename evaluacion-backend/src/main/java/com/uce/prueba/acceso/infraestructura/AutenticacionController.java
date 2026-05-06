package com.uce.prueba.acceso.infraestructura;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uce.prueba.acceso.aplicacion.AutenticarEvaluado;
import com.uce.prueba.acceso.aplicacion.RegistrarEvaluado;
import com.uce.prueba.acceso.dominio.model.Credencial;
import com.uce.prueba.acceso.dominio.model.Evaluado;
import com.uce.prueba.acceso.infraestructura.AutenticacionDTOs.AutenticacionResponse;
import com.uce.prueba.acceso.infraestructura.AutenticacionDTOs.LoginRequest;
import com.uce.prueba.acceso.infraestructura.AutenticacionDTOs.RegistroRequest;

@RestController
@RequestMapping("/api/autenticacion")
public class AutenticacionController {

    private final RegistrarEvaluado registrarEvaluado;
    private final AutenticarEvaluado autenticarEvaluado;

    public AutenticacionController(RegistrarEvaluado registrarEvaluado, AutenticarEvaluado autenticarEvaluado) {
        this.registrarEvaluado = registrarEvaluado;
        this.autenticarEvaluado = autenticarEvaluado;
    }

    @PostMapping("/registro")
    public ResponseEntity<AutenticacionResponse> registrar(@RequestBody RegistroRequest request) {
        Credencial credencial = new Credencial(request.email(), request.password());
        Evaluado evaluado = registrarEvaluado.ejecutar(request.nombre(), credencial);
        
        AutenticacionResponse response = new AutenticacionResponse(
                evaluado.getId(),
                evaluado.getNombre(),
                evaluado.getCredencial().getEmail(), // Accedemos a través del VO Credencial
                "token-jwt-mock" // En un caso real se genera un JWT
        );
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AutenticacionResponse> login(@RequestBody LoginRequest request) {
        Credencial credencial = new Credencial(request.email(), request.password());
        Evaluado evaluado = autenticarEvaluado.ejecutar(credencial);
        
        AutenticacionResponse response = new AutenticacionResponse(
                evaluado.getId(),
                evaluado.getNombre(),
                evaluado.getCredencial().getEmail(), // Accedemos a través del VO Credencial
                "token-jwt-mock" // En un caso real se genera un JWT
        );
        
        return ResponseEntity.ok(response);
    }
}