package com.uce.prueba.evaluacion.dominio.model;

public class Reactivo {
    
    private final String id;
    private final String texto;
    private final String opcionCorrecta;
    
    public Reactivo(String id, String texto, String opcionCorrecta) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El Reactivo debe tener un ID válido");
        }
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El Reactivo debe tener un texto");
        }
        if (opcionCorrecta == null || opcionCorrecta.isBlank()) {
            throw new IllegalArgumentException("El Reactivo debe tener una opción correcta");
        }
        this.id = id;
        this.texto = texto;
        this.opcionCorrecta = opcionCorrecta.toLowerCase();
    }
    
    public String getId() { return id; }
    public String getTexto() { return texto; }
    public String getOpcionCorrecta() { return opcionCorrecta; }
    
    public boolean esCorrecta(String opcionSeleccionada) {
        return this.opcionCorrecta.equals(opcionSeleccionada);
    }
}