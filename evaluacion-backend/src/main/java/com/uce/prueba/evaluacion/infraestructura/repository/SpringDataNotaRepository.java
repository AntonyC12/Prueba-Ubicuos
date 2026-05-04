package com.uce.prueba.evaluacion.infraestructura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataNotaRepository extends JpaRepository<NotaEntity, Long> {
    Optional<NotaEntity> findByEvaluadoId(Long evaluadoId);
}