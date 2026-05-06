document.addEventListener('DOMContentLoaded', async () => {
    // 1. EL GUARD DE SEGURIDAD
    // Revisamos si el usuario pasó por el login revisando el SessionStorage
    const estado = sessionStorage.getItem('estadoEvaluado');
    const evaluadoId = sessionStorage.getItem('evaluadoId');

    if (estado !== 'Autenticado' || !evaluadoId) {
        // Si no está autenticado, lo botamos al login sin piedad
        window.location.href = '../pages/login.html';
        return; 
    }

    const contenedor = document.getElementById('contenedorReactivos');
    const form = document.getElementById('evaluacionForm');
    const btnFinalizar = document.getElementById('btnFinalizar');
    const mensajeAlerta = document.getElementById('mensajeAlerta');

    // 2. OBTENER LAS PREGUNTAS DEL BACKEND
    try {
        const response = await fetch('http://localhost:8080/api/evaluacion/reactivos');
        if (!response.ok) throw new Error('Error al conectar con el servidor.');
        
        const data = await response.json();
        renderizarReactivos(data.reactivos);
        btnFinalizar.style.display = 'block'; // Mostramos el botón cuando hay preguntas
    } catch (error) {
        mostrarAlerta(error.message, 'error');
        contenedor.innerHTML = '<p style="text-align:center; color:red;">No se pudieron cargar las preguntas.</p>';
    }

    // Función para dibujar el HTML de cada pregunta
    function renderizarReactivos(reactivos) {
        contenedor.innerHTML = ''; // Limpiamos el mensaje de "Cargando..."

        reactivos.forEach((reactivo, index) => {
            const divCard = document.createElement('div');
            divCard.className = 'reactivo-card';

            const preguntaTitulo = document.createElement('div');
            preguntaTitulo.className = 'reactivo-pregunta';
            preguntaTitulo.textContent = `${index + 1}. ${reactivo.texto}`;
            divCard.appendChild(preguntaTitulo);

            reactivo.opciones.forEach(opcion => {
                const label = document.createElement('label');
                label.className = 'opcion-label';
                
                const input = document.createElement('input');
                input.type = 'radio';
                input.name = reactivo.id; // Nombre = R001, R002, etc. ¡Esto agrupa los radio buttons!
                input.value = opcion.charAt(0); // Captura solo la letra (a, b, c o d) para mandar al backend
                input.className = 'opcion-input';
                input.required = true; // Obliga a responder todas

                label.appendChild(input);
                label.appendChild(document.createTextNode(opcion));
                divCard.appendChild(label);
            });

            contenedor.appendChild(divCard);
        });
    }

    // 3. ENVIAR LAS RESPUESTAS AL BACKEND
    form.addEventListener('submit', async (evento) => {
        evento.preventDefault();
        
        // Armamos el JSON exactamente como lo pide el RespuestaRequestItem de tu backend
        const respuestasData = [];
        const formData = new FormData(form);
        
        for (let [idReactivo, idOpcion] of formData.entries()) {
            respuestasData.push({
                reactivoId: idReactivo,
                opcionId: idOpcion
            });
        }

        try {
            // Fíjate cómo usamos el evaluadoId del SessionStorage en la URL
            const response = await fetch(`http://localhost:8080/api/evaluacion/${evaluadoId}/finalizar`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(respuestasData)
            });

            if (!response.ok) throw new Error('Error al enviar el examen.');

            const resultado = await response.json();
            // Guardamos los datos para la siguiente página
            sessionStorage.setItem('resultado', JSON.stringify(resultado));
            console.log(resultado)
            
            // Éxito: Bloqueamos el botón y mostramos el mensaje del backend
            mostrarAlerta(resultado.mensaje, 'success');
            btnFinalizar.disabled = true;
            btnFinalizar.textContent = 'Procesando...';

            // Limpiamos la sesión y lo mandamos al login tras 4 segundos
            // Nueva url: analisis.html
            setTimeout(() => {
                window.location.href = '../pages/analisis.html';
            }, 4000);

        } catch (error) {
            mostrarAlerta(error.message, 'error');
        }
    });

    function mostrarAlerta(mensaje, tipo) {
        mensajeAlerta.textContent = mensaje;
        mensajeAlerta.style.display = 'block';
        if (tipo === 'success') {
            mensajeAlerta.style.backgroundColor = 'var(--success-bg)';
            mensajeAlerta.style.color = 'var(--success-text)';
            mensajeAlerta.style.border = '1px solid #86efac';
        } else {
            mensajeAlerta.style.backgroundColor = 'var(--error-bg)';
            mensajeAlerta.style.color = 'var(--error-text)';
            mensajeAlerta.style.border = '1px solid #fca5a5';
        }
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }
});