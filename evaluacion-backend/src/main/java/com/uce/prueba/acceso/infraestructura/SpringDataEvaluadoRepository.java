package com.uce.prueba.acceso.infraestructura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataEvaluadoRepository extends JpaRepository<EvaluadoEntity, Long> {
    
    Optional<EvaluadoEntity> findByEmail(String email);
}