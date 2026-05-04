document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');
    const mensajeAlerta = document.getElementById('mensajeError'); // ID corregido
    const btnLogin = document.getElementById('btnIngresar'); // ID corregido

    form.addEventListener('submit', async (evento) => {
        evento.preventDefault();

        mensajeAlerta.style.display = 'none';

        // CAPTURA (KAN-13) - IDs corregidos
        const credencial = document.getElementById('credencialEmail').value.trim();
        const password = document.getElementById('credencialPassword').value.trim();

        // VALIDACIONES (KAN-14)
        if (!credencial || !password) {
            mostrarAlerta('Por favor, ingresa tu credencial y contraseña.', 'error');
            return;
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(credencial)) {
            mostrarAlerta('El formato de la credencial no es válido.', 'error');
            return;
        }

        // MAPEO
        const loginData = {
            email: credencial,
            password: password
        };

        try {
            const response = await fetch('http://localhost:8080/api/autenticacion/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(loginData)
            });

            if (!response.ok) {
                throw new Error('Credenciales incorrectas. Intenta de nuevo.');
            }

            const data = await response.json();
            mostrarAlerta('¡Bienvenido! Entrando al sistema...', 'success');

            // KAN-32: Guardamos el ID
            sessionStorage.setItem('evaluadoId', data.evaluadoId || data.id);
            
            // KAN-31: Emitimos el estado para el Guard (¡Esto evita que te rebote!)
            sessionStorage.setItem('estadoEvaluado', 'Autenticado');
            
            setTimeout(() => {
                window.location.href = 'evaluacion.html';
            }, 1000);

        } catch (error) {
            mostrarAlerta(error.message, 'error');
        }
    });

    function mostrarAlerta(mensaje, tipo) {
        mensajeAlerta.textContent = mensaje;
        mensajeAlerta.style.display = 'block';
        
        // Ajuste de colores rápido por si es éxito o error
        if (tipo === 'success') {
            mensajeAlerta.style.backgroundColor = '#dcfce7';
            mensajeAlerta.style.color = '#166534';
            mensajeAlerta.style.borderColor = '#86efac';
        } else {
            mensajeAlerta.style.backgroundColor = 'var(--error-bg)';
            mensajeAlerta.style.color = 'var(--error-text)';
            mensajeAlerta.style.borderColor = '#fca5a5';
        }
    }
});