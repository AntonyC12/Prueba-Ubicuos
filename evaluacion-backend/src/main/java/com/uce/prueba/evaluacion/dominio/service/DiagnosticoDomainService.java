package com.uce.prueba.evaluacion.dominio.service;

import com.uce.prueba.evaluacion.dominio.model.NivelComprension;
import com.uce.prueba.evaluacion.dominio.model.Nota;
import com.uce.prueba.evaluacion.dominio.model.Resultado;

/**
 * Servicio de Dominio encargado de la lógica pura de diagnóstico.
 * Transforma un Resultado en una Nota y un Nivel de Comprensión.
 */
public class DiagnosticoDomainService {
    
    public Nota calcularDiagnostico(Resultado resultado) {
        double porcentaje = resultado.getPorcentaje();
        NivelComprension nivel = NivelComprension.fromPorcentaje(porcentaje);
        String valorNota = nivel.getNota();
        
        return new Nota(
            nivel, 
            valorNota, 
            resultado.getAciertos(), 
            resultado.getTotal()
        );
    }
}
