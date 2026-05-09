document.addEventListener('DOMContentLoaded', () => {
    // 1. Enlazamos los elementos del HTML
    const form = document.getElementById('registroForm');
    const mensajeAlerta = document.getElementById('mensajeAlerta');

    if (!form) {
        console.error("Error: No se encontró el formulario 'registroForm' en el HTML.");
        return; 
    }

    // 2. Interceptamos el clic del botón
    form.addEventListener('submit', async (evento) => {
        evento.preventDefault(); 
        
        mensajeAlerta.style.display = 'none';

        // 3. Capturamos los datos
        const credencial = document.getElementById('registroCredencial').value.trim();
        const nombre = document.getElementById('registroNombre').value.trim();
        const password = document.getElementById('registroPassword').value.trim();

        // 4. Validaciones de la KAN-14
        if (!credencial || !nombre || !password) {
            mostrarAlerta('Todos los campos son obligatorios.', 'error');
            return;
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(credencial)) {
            mostrarAlerta('El formato de la credencial no es válido.', 'error');
            return;
        }

        // 5. Preparamos los datos para enviar al Backend (KAN-13)
        const nuevoEvaluado = {
            email: credencial, // Enviamos como 'email' para que Java lo entienda
            nombre: nombre,
            password: password
        };

        // 6. Enviamos a Spring Boot
        try {
            const response = await fetch('http://18.219.237.246:8080/api/autenticacion/registro', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevoEvaluado)
            });

            if (!response.ok) throw new Error('Error al registrar en la base de datos');

            // 7. Éxito: Mostramos mensaje y limpiamos el formulario
            mostrarAlerta('¡Registro exitoso! Por favor, haz clic en "Inicia sesión aquí" para continuar.', 'success');
            form.reset();

        } catch (error) {
            mostrarAlerta(error.message, 'error');
        }
    });

    // Función de diseño para las alertas (KAN-33)
    function mostrarAlerta(mensaje, tipo) {
        mensajeAlerta.textContent = mensaje;
        mensajeAlerta.className = 'alert-message';
        mensajeAlerta.classList.add(tipo === 'error' ? 'alert-error' : 'alert-success');
        mensajeAlerta.style.display = 'block';
    }
});
