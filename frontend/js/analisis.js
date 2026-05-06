const evaluadoId = sessionStorage.getItem('evaluadoId');

        if (!evaluadoId) {
            window.location.href = '../pages/login.html';
        }

        async function obtenerResultado() {
            try {
                const response = await fetch(`http://localhost:8080/api/evaluacion/${evaluadoId}/resultado`);
                const data = await response.json();

                console.log(data);

                if (data.estado === 'PROCESANDO') {
                    document.getElementById('puntaje').textContent = data.mensaje || "Procesando examen...";
                    document.getElementById('nota').textContent = '...';
                    document.getElementById('nivel').textContent = '';

                    // vuelve a intentar en 3 segundos
                    setTimeout(obtenerResultado, 3000);
                } else {
                    document.getElementById('puntaje').textContent = `Resultado: ${data.resultado}`;
                    document.getElementById('nota').textContent = data.nota;
                    document.getElementById('nivel').textContent = `Nivel: ${data.nivel}`;
                }

            } catch (error) {
                console.error(error);
                document.getElementById('puntaje').textContent = "Error al obtener resultado";
            }
        }

        obtenerResultado();

        function volver() {
            sessionStorage.clear();
            window.location.href = '../pages/login.html';
        }