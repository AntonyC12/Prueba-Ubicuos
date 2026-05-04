package com.uce.prueba.evaluacion.dominio.repository;

import com.uce.prueba.evaluacion.dominio.model.Nota;
import java.util.Optional;

public interface NotaRepository {
    Nota guardar(Long evaluadoId, Nota nota);
    Optional<Nota> buscarPorEvaluadoId(Long evaluadoId);
}