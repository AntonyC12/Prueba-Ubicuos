document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');
    const mensajeAlerta = document.getElementById('mensajeAlerta');
    const btnLogin = document.getElementById('btnLogin');

    form.addEventListener('submit', async (evento) => {
        evento.preventDefault();

        mensajeAlerta.style.display = 'none';
        mensajeAlerta.className = 'alert-message';

        // CAPTURA (KAN-13)
        const credencial = document.getElementById('loginCredencial').value.trim();
        const password = document.getElementById('loginPassword').value.trim();

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

            sessionStorage.setItem('evaluadoId', data.evaluadoId);
            
            setTimeout(() => {
                window.location.href = 'evaluacion.html';
            }, 1000);

        } catch (error) {
            mostrarAlerta(error.message, 'error');
        }
    });

    function mostrarAlerta(mensaje, tipo) {
        mensajeAlerta.textContent = mensaje;
        mensajeAlerta.classList.add(tipo === 'error' ? 'alert-error' : 'alert-success');
        mensajeAlerta.style.display = 'block';
    }
});