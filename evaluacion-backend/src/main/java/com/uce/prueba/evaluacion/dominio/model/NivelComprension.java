package com.uce.prueba.evaluacion.dominio.model;

public enum NivelComprension {
    
    BASICO(0, 49, "C"),
    INTERMEDIO(50, 69, "B"),
    AVANZADO(70, 100, "A");
    
    private final int minimo;
    private final int maximo;
    private final String nota;
    
    NivelComprension(int minimo, int maximo, String nota) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nota = nota;
    }
    
    public int getMinimo() { return minimo; }
    public int getMaximo() { return maximo; }
    public String getNota() { return nota; }
    
    public static NivelComprension fromPorcentaje(double porcentaje) {
        if (porcentaje >= AVANZADO.minimo) return AVANZADO;
        if (porcentaje >= INTERMEDIO.minimo) return INTERMEDIO;
        return BASICO;
    }
}