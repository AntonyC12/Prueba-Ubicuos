# Proyecto: Prueba-Ubicuos (Motor de Evaluación DDD)

Este proyecto implementa un sistema de evaluación técnica diseñado bajo los principios de **Domain-Driven Design (DDD)** y **Arquitectura Hexagonal**. El objetivo principal es garantizar que la lógica de negocio sea independiente de la tecnología y que el software refleje fielmente el conocimiento de los expertos del dominio.

## 🧠 Conceptos Fundamentales

### 1. Lenguaje Ubicuo (Ubiquitous Language)
El Lenguaje Ubicuo es el pilar de DDD. Es un lenguaje compartido y común desarrollado por el equipo técnico y los expertos de negocio para ser utilizado de forma consistente en:
*   La documentación (Glosarios, Diagramas).
*   Las conversaciones del equipo.
*   **El código fuente (Nombres de clases, métodos y variables).**

**¿Para qué se usa?**
Para eliminar la brecha de comunicación (traducción) entre el negocio y la tecnología, reduciendo malentendidos y asegurando que el código sea una representación directa del modelo mental del negocio.

### 2. Contextos Acotados (Bounded Contexts)
Hemos dividido el sistema en límites semánticos claros para evitar el acoplamiento y la complejidad:

*   **📦 Contexto de Acceso (BC_Acceso):** Gestiona la identidad, autenticación y persistencia de datos básicos del **Evaluado**. Su foco es la seguridad y el registro.
*   **📦 Contexto de Evaluación (BC_Evaluacion):** El corazón del sistema. Gestiona **Reactivos**, procesa **Respuestas** y genera un **Diagnóstico** basado en los resultados.

---

## 🏗️ Integración Arquitectónica

El proyecto utiliza una **Arquitectura Hexagonal (Puertos y Adaptadores)** para proteger el dominio:

### Estructura de Capas
1.  **Dominio (`.dominio`):** Contiene la lógica de negocio pura.
    *   **Modelos:** `Reactivo`, `Respuesta`, `Nota`, `Resultado` (Value Objects y Entidades).
    *   **Servicios:** `DiagnosticoDomainService` (Lógica de cálculo sin dependencias externas).
    *   **Repositorios (Puertos):** Interfaces que definen cómo persistir datos sin mencionar tecnologías (JPA, SQL).
2.  **Aplicación (`.aplicacion`):** Orquestación de casos de uso.
    *   `RealizarExamen`: Valida reglas de negocio (Unicidad) y publica eventos.
    *   `ServicioDiagnostico`: Escucha eventos y coordina el flujo de diagnóstico.
3.  **Infraestructura (`.infraestructura`):** Detalles técnicos y adaptadores.
    *   Controladores REST (Entrada).
    *   Implementaciones JPA/Spring Data (Salida).

---

## 🛡️ Reglas de Negocio Protegidas (Invariantes)

El sistema garantiza automáticamente el cumplimiento de las siguientes reglas críticas:

*   **Unicidad de Evaluación:** Un **Evaluado** no puede iniciar un nuevo test si ya posee un diagnóstico procesado.
*   **Integridad de Respuesta:** Ninguna **Respuesta** puede existir si no está vinculada a un **Evaluado** y un **Reactivo** específico.
*   **Cálculo Protegido:** La **Nota** es generada exclusivamente por el `DiagnosticoDomainService` basándose en el peso de los reactivos correctos; no se permiten ediciones manuales.
*   **Inmutabilidad:** Una vez generado el **Diagnóstico**, tanto la **Nota** como el **Nivel de Comprensión** son inalterables.

---

## 🚀 Cómo Ejecutar el Proyecto

1.  Asegúrate de tener **Java 17+** y **Maven** instalado.
2.  Clona el repositorio.
3.  Navega a la carpeta `evaluacion-backend`.
4.  Ejecuta el comando:
    ```bash
    mvn spring-boot:run
    ```
5.  Accede a la API en `http://localhost:8080/api/evaluacion`.

---

**Desarrollado con rigor arquitectónico para asegurar la escalabilidad y mantenibilidad del sistema.**