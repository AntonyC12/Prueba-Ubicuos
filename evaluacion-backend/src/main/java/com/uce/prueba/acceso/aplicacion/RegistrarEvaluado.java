package com.uce.prueba.acceso.aplicacion;

import com.uce.prueba.acceso.dominio.model.Credencial;
import com.uce.prueba.acceso.dominio.model.Evaluado;
import com.uce.prueba.acceso.dominio.repository.EvaluadoRepository;
import com.uce.prueba.acceso.infraestructura.EncriptadorPassword;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegistrarEvaluado {

    private final EvaluadoRepository evaluadoRepository;
    private final EncriptadorPassword encriptadorPassword;

    public RegistrarEvaluado(EvaluadoRepository evaluadoRepository, EncriptadorPassword encriptadorPassword) {
        this.evaluadoRepository = evaluadoRepository;
        this.encriptadorPassword = encriptadorPassword;
    }

    public Evaluado ejecutar(String nombre, Credencial credencial) {
        Optional<Evaluado> evaluadoExistente = evaluadoRepository.buscarPorEmail(credencial.getEmail());
        
        if (evaluadoExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un evaluado registrado con el correo: " + credencial.getEmail());
        }

        // Encriptar la contraseña usando el valor original de la credencial
        String hashedPassword = encriptadorPassword.encriptar(credencial.getPassword());
        
        // Creamos una nueva credencial con el hash para no exponer el texto plano en el dominio
        Credencial credencialEncriptada = new Credencial(credencial.getEmail(), hashedPassword);

        Evaluado nuevoEvaluado = new Evaluado(
                null, 
                nombre, 
                credencialEncriptada, 
                LocalDateTime.now()
        );

        return evaluadoRepository.guardar(nuevoEvaluado);
    }
}