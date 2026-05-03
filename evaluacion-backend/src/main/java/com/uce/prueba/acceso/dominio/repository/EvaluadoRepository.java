package com.uce.prueba.acceso.dominio.repository;

import com.uce.prueba.acceso.dominio.model.Evaluado;
import java.util.Optional;
import java.util.List;

public interface EvaluadoRepository {
    
    /**
     * Guarda un evaluado en el sistema (persistencia o actualización).
     */
    Evaluado guardar(Evaluado evaluado);

    /**
     * Busca un evaluado por su identificador único.
     */
    Optional<Evaluado> buscarPorId(Long id);

    /**
     * Busca un evaluado por su correo electrónico (útil para login y validación de duplicados).
     */
    Optional<Evaluado> buscarPorEmail(String email);

    /**
     * Retorna todos los evaluados registrados en el sistema.
     */
    List<Evaluado> obtenerTodos();

    /**
     * Elimina un evaluado por su ID.
     */
    void eliminar(Long id);

    // Nuevo método para obtener la contraseña encriptada sin exponer toda la entidad al dominio
    Optional<String> obtenerPasswordPorEmail(String email);
}