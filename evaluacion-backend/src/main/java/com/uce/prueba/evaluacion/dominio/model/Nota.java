package com.uce.prueba.evaluacion.dominio.model;

public class Nota {
    
    private final String valor;
    private final NivelComprension nivel;
    private final int puntaje;
    private final int total;
    
    public Nota(NivelComprension nivel, String valor, int puntaje, int total) {
        if (nivel == null) {
            throw new IllegalArgumentException("El nivel es obligatorio");
        }
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El valor de la nota es obligatorio");
        }
        if (puntaje < 0 || puntaje > total) {
            throw new IllegalArgumentException("El puntaje no puede ser negativo ni mayor al total");
        }
        if (total <= 0) {
            throw new IllegalArgumentException("El total debe ser positivo");
        }
        this.nivel = nivel;
        this.valor = valor.toUpperCase();
        this.puntaje = puntaje;
        this.total = total;
    }
    
    public String getValor() { return valor; }
    public NivelComprension getNivel() { return nivel; }
    public int getPuntaje() { return puntaje; }
    public int getTotal() { return total; }
    
    public double getPorcentaje() {
        return (double) puntaje / total * 100;
    }
}