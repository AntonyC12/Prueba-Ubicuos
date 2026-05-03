package com.uce.prueba.acceso.infraestructura;

public class AutenticacionDTOs {

    public record RegistroRequest(String nombre, String email, String password) {}
    
    public record LoginRequest(String email, String password) {}

    public record AutenticacionResponse(Long id, String nombre, String email, String token) {}
}