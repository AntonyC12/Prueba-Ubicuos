document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');
    const mensajeAlerta = document.getElementById('mensajeError');
    const btnLogin = document.getElementById('btnIngresar');

    form.addEventListener('submit', async (evento) => {
        evento.preventDefault();

        mensajeAlerta.style.display = 'none';

        const credencial = document.getElementById('credencialEmail').value.trim();
        const password = document.getElementById('credencialPassword').value.trim();

        if (!credencial || !password) {
            mostrarAlerta('Por favor, ingresa tu credencial y contraseña.', 'error');
            return;
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(credencial)) {
            mostrarAlerta('El formato de la credencial no es válido.', 'error');
            return;
        }

        const loginData = {
            email: credencial,
            password: password
        };

        try {
            const response = await fetch('http://18.219.237.246:8080/api/autenticacion/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(loginData)
            });

            if (!response.ok) {
                throw new Error('Credenciales incorrectas. Intenta de nuevo.');
            }

            const data = await response.json();
            mostrarAlerta('¡Bienvenido! Verificando estado...', 'success');

            const evaluadoId = data.evaluadoId || data.id;

            // Guardamos sesión
            sessionStorage.setItem('evaluadoId', evaluadoId);
            sessionStorage.setItem('estadoEvaluado', 'Autenticado');

            // 🔥 NUEVO: verificar si ya hizo el examen
            setTimeout(() => {
                verificarEstado(evaluadoId);
            }, 1000);

        } catch (error) {
            mostrarAlerta(error.message, 'error');
        }
    });

    // revisa el estado del evaluado para decidir a dónde enviarlo
    async function verificarEstado(evaluadoId) {
        try {
            const response = await fetch(`http://18.219.237.246:8080/api/evaluacion/${evaluadoId}/resultado`);
            const data = await response.json();

            console.log('Estado evaluacion:', data);

            if (data.estado === 'FINALIZADO') {
                // ✅ Ya respondió → va a resultados
                window.location.href = '../pages/analisis.html';
            } else {
                // 🟢 No ha respondido → va al examen
                window.location.href = '../pages/evaluacion.html';
            }

        } catch (error) {
            console.error(error);
            // fallback por si falla el endpoint
            window.location.href = '../pages/evaluacion.html';
        }
    }

    function mostrarAlerta(mensaje, tipo) {
        mensajeAlerta.textContent = mensaje;
        mensajeAlerta.style.display = 'block';
        
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
