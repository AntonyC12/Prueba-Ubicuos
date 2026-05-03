package com.uce.prueba.acceso;

import com.uce.prueba.acceso.aplicacion.AutenticarEvaluado;
import com.uce.prueba.acceso.aplicacion.RegistrarEvaluado;
import com.uce.prueba.acceso.dominio.model.Credencial;
import com.uce.prueba.acceso.dominio.model.Evaluado;
import com.uce.prueba.acceso.dominio.repository.EvaluadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AutenticacionIntegrationTest {

    @Autowired
    private RegistrarEvaluado registrarEvaluado;

    @Autowired
    private AutenticarEvaluado autenticarEvaluado;

    @Autowired
    private EvaluadoRepository evaluadoRepository;

    private String email;

    @BeforeEach
    void setUp() {
        // Genera email único para evitar conflictos en Postgres
        email = "juan_" + System.currentTimeMillis() + "@uce.edu.ec";

        // Limpieza opcional (si tienes método deleteAll, mejor)
        evaluadoRepository.obtenerTodos()
                .forEach(e -> evaluadoRepository.eliminar(e.getId()));
    }

    @Test
    void debeRegistrarYAutenticarEvaluadoCorrectamente() {

        // 1. Datos de prueba
        String nombre = "Juan Pérez";
        String password = "password123";

        Credencial credencial = new Credencial(email, password);

        // 2. REGISTRO (GUARDA EN POSTGRES)
        Evaluado evaluadoRegistrado =
                registrarEvaluado.ejecutar(nombre, credencial);

        assertNotNull(evaluadoRegistrado.getId());
        assertEquals(email, evaluadoRegistrado.getCredencial().getEmail());

        // 3. AUTENTICACIÓN (LEE DESDE POSTGRES)
        Evaluado evaluadoAutenticado =
                autenticarEvaluado.ejecutar(credencial);

        assertEquals(evaluadoRegistrado.getId(), evaluadoAutenticado.getId());
    }
}