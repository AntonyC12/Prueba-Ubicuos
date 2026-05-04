package com.uce.prueba.evaluacion.infraestructura.repository;

import com.uce.prueba.evaluacion.dominio.model.NivelComprension;
import com.uce.prueba.evaluacion.dominio.model.Nota;
import com.uce.prueba.evaluacion.dominio.repository.NotaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class JpaNotaRepository implements NotaRepository {
    
    private final SpringDataNotaRepository springDataNotaRepository;
    
    public JpaNotaRepository(SpringDataNotaRepository springDataNotaRepository) {
        this.springDataNotaRepository = springDataNotaRepository;
    }
    
    @Override
    public Nota guardar(Long evaluadoId, Nota nota) {
        NotaEntity entity = new NotaEntity(
            evaluadoId,
            nota.getValor(),
            nota.getNivel().name(),
            nota.getPuntaje(),
            nota.getTotal(),
            nota.getPorcentaje()
        );
        springDataNotaRepository.save(entity);
        return nota;
    }
    
    @Override
    public Optional<Nota> buscarPorEvaluadoId(Long evaluadoId) {
        return springDataNotaRepository.findByEvaluadoId(evaluadoId)
            .map(entity -> new Nota(
                NivelComprension.valueOf(entity.getNivel()),
                entity.getValor(),
                entity.getPuntaje(),
                entity.getTotal()
            ));
    }
}