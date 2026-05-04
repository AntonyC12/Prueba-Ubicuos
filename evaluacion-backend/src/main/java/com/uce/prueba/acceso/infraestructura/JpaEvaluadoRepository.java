package com.uce.prueba.acceso.infraestructura;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.uce.prueba.acceso.dominio.model.Credencial;
import com.uce.prueba.acceso.dominio.model.Evaluado;
import com.uce.prueba.acceso.dominio.repository.EvaluadoRepository;

@Repository
public class JpaEvaluadoRepository implements EvaluadoRepository {

    private final SpringDataEvaluadoRepository repository;

    public JpaEvaluadoRepository(SpringDataEvaluadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Evaluado guardar(Evaluado evaluado) {
        EvaluadoEntity entity = toEntity(evaluado);
        EvaluadoEntity entitySaved = repository.save(entity);
        return toDomain(entitySaved);
    }

    @Override
    public Optional<Evaluado> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Evaluado> buscarPorEmail(String email) {
        return repository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public List<Evaluado> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<String> obtenerPasswordPorEmail(String email) {
        return repository.findByEmail(email)
                .map(EvaluadoEntity::getPassword);
    }

    private EvaluadoEntity toEntity(Evaluado evaluado) {
        EvaluadoEntity entity = new EvaluadoEntity();

        if (evaluado.getId() != null) {
            entity.setId(Long.valueOf(evaluado.getId()));
        }

        entity.setNombre(evaluado.getNombre());
        entity.setEmail(evaluado.getCredencial().getEmail());
        entity.setPassword(evaluado.getCredencial().getPassword());
        entity.setFechaRegistro(evaluado.getFechaRegistro());

        return entity;
    }

    private Evaluado toDomain(EvaluadoEntity entity) {
    Credencial credencial = new Credencial(entity.getEmail(), entity.getPassword());
    return new Evaluado(
            entity.getId(), 
            entity.getNombre(),
            credencial,
            entity.getFechaRegistro()
    );
}
}
