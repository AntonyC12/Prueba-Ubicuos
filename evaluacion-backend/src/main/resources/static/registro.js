document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('registroForm');
    const mensajeAlerta = document.getElementById('mensajeAlerta');

    if (!form) {
        console.error("Error: No se encontró el formulario 'registroForm' en el HTML.");
        return;
    }

    form.addEventListener('submit', async (evento) => {
        evento.preventDefault();

        mensajeAlerta.style.display = 'none';

        const credencial = document.getElementById('registroCredencial').value.trim();
        const nombre = document.getElementById('registroNombre').value.trim();
        const password = document.getElementById('registroPassword').value.trim();

        //KAN 33 Validación de campos
        if (!credencial || !nombre || !password) {
            mostrarAlerta('Todos los campos son obligatorios.', 'error');
            return;
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(credencial)) {
            mostrarAlerta('El formato de la credencial no es válido.', 'error');
            return;
        }

        const nuevoEvaluado = {
            email: credencial,
            nombre: nombre,
            password: password
        };

        try {
            const response = await fetch('http://localhost:8080/api/autenticacion/registro', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevoEvaluado)
            });

            if (!response.ok) throw new Error('Error al registrar en la base de datos');

            const data = await response.json();

            // KAN-32: Guardamos el ID de forma segura en la sesión del navegador
            sessionStorage.setItem('evaluadoId', data.id); // Asegúrate de que tu backend devuelve 'id', si no, pon el nombre correcto.

            // KAN-31: Emitimos el estado global
            sessionStorage.setItem('estadoEvaluado', 'Autenticado');


            mostrarAlerta('¡Registro exitoso! Redirigiendo...', 'success');

            setTimeout(() => {
                window.location.href = 'evaluacion.html';
            }, 2000);

        } catch (error) {
            mostrarAlerta(error.message, 'error');
        }
    });

    function mostrarAlerta(mensaje, tipo) {
        mensajeAlerta.textContent = mensaje;
        mensajeAlerta.className = 'alert-message';
        mensajeAlerta.classList.add(tipo === 'error' ? 'alert-error' : 'alert-success');
        mensajeAlerta.style.display = 'block';
    }
});