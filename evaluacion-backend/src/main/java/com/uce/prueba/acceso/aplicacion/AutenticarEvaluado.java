package com.uce.prueba.acceso.aplicacion;

import com.uce.prueba.acceso.dominio.model.Credencial;
import com.uce.prueba.acceso.dominio.model.Evaluado;
import com.uce.prueba.acceso.dominio.repository.EvaluadoRepository;
import com.uce.prueba.acceso.infraestructura.EncriptadorPassword;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticarEvaluado {

    private final EvaluadoRepository evaluadoRepository;
    private final EncriptadorPassword encriptadorPassword;

    public AutenticarEvaluado(EvaluadoRepository evaluadoRepository, EncriptadorPassword encriptadorPassword) {
        this.evaluadoRepository = evaluadoRepository;
        this.encriptadorPassword = encriptadorPassword;
    }

    public Evaluado ejecutar(Credencial credencial) {
        // 1. Validación de existencia
        Optional<Evaluado> evaluadoOpt = evaluadoRepository.buscarPorEmail(credencial.getEmail());

        if (evaluadoOpt.isEmpty()) {
            throw new IllegalArgumentException("El usuario con el email proporcionado no está registrado.");
        }

        Evaluado evaluado = evaluadoOpt.get();

        // 2. Obtener la contraseña desde la base de datos (PostgreSQL)
        String passwordAlmacenado = evaluadoRepository.obtenerPasswordPorEmail(credencial.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la contraseña del evaluado."));

        // 3. Verificar si la contraseña ingresada coincide con el hash encriptado
        boolean esValido = encriptadorPassword.verificar(credencial.getPassword(), passwordAlmacenado);

        if (!esValido) {
            throw new IllegalArgumentException("La contraseña es incorrecta.");
        }

        return evaluado;
    }
}