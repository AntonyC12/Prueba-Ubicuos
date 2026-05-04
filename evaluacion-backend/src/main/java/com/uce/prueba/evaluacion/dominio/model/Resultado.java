package com.uce.prueba.evaluacion.dominio.model;

/**
 * Value Object que representa el puntaje crudo obtenido tras finalizar el test.
 * Definido en el Glosario de Lenguaje Ubicuo.
 */
public class Resultado {
    
    private final int aciertos;
    private final int total;
    
    public Resultado(int aciertos, int total) {
        if (aciertos < 0 || aciertos > total) {
            throw new IllegalArgumentException("Los aciertos no pueden ser negativos ni mayores al total");
        }
        if (total <= 0) {
            throw new IllegalArgumentException("El total debe ser positivo");
        }
        this.aciertos = aciertos;
        this.total = total;
    }
    
    public int getAciertos() { return aciertos; }
    public int getTotal() { return total; }
    
    public double getPorcentaje() {
        return (double) aciertos / total * 100;
    }
    
    @Override
    public String toString() {
        return aciertos + "/" + total + " (" + String.format("%.1f", getPorcentaje()) + "%)";
    }
}
