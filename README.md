# Culturalia API REST - Gestión de Talleres Culturales

Bienvenido a la API REST de **Culturalia**, una plataforma diseñada para gestionar de forma eficiente talleres culturales, sus organizadores y la inscripción de participantes.

Este proyecto ha sido desarrollado como parte del módulo de Acceso a Datos y Programación de Servicios y Procesos (Curso 2026-2027) en el Colegio Salesianos San Pedro (Triana).

## 🚀 Tecnologías Utilizadas

* **Java 17 / 21**

* **Spring Boot 3.x**

  * Spring Data JPA

  * Spring Web (MVC)

  * Spring Validation

* **H2 Database** (Base de datos en memoria)

* **Lombok**

* **OpenAPI 3.0 / Swagger UI** (Documentación interactiva de la API)

* **Postman** (Pruebas de endpoints)

## 🏛️ Modelo de Datos

El modelo de datos está compuesto por tres entidades principales:

1. **Organizador (`Organizador`)**: Representa a la persona u entidad responsable de un taller.

   * Relación: `1:N` con **Taller** (Un organizador puede impartir varios talleres).

2. **Taller (`Taller`)**: Representa un taller cultural ofrecido por la plataforma.

   * Posee título único y rango de fechas/horas (`fechaHoraInicio` y `fechaHoraFin`).

   * Relación: `N:1` con **Organizador** y `N:M` con **Participante**.

3. **Participante (`Participante`)**: Representa a un asistente inscrito en la plataforma.

   * Relación: `N:M` con **Taller** (Un participante puede inscribirse en múltiples talleres sin duplicar la inscripción).

## 🛠️ Requisitos Funcionales Implementados

### 1. Gestión de Organizadores

* `GET /organizaciones` - Listar todos los organizadores.

* `GET /organizaciones/{id}` - Obtener detalle de un organizador.

* `POST /organizaciones` - Registrar un nuevo organizador.

* `PUT /organizaciones/{id}` - Modificar los datos de un organizador.

* `DELETE /organizaciones/{id}` - Eliminar un organizador.

### 2. Gestión de Talleres

* `GET /talleres` - Listar todos los talleres culturales.

* `GET /talleres/{id}` - Obtener información detallada de un taller.

* `POST /talleres` - Crear un taller.

  * *Validación especial*: El título se valida ignorando mayúsculas/minúsculas y espacios innecesarios.

  * *Validación personalizada*: `@FechaFinPosteriorAInicio` garantiza que la fecha de fin sea estrictamente posterior a la de inicio.

* `PUT /talleres/{id}` - Actualizar los datos de un taller.

* `DELETE /talleres/{id}` - Eliminar un taller.

### 3. Gestión de Participantes e Inscripciones (M:M)

* `GET /participantes` - Listar participantes.

* `POST /participantes` - Registrar nuevo participante.

* `POST /talleres/{tallerId}/participantes/{participanteId}` - Inscribir a un participante en un taller (evita inscripciones duplicadas mediante `ParticipanteAlreadyRegisteredException`).

* `GET /talleres/{tallerId}/participantes` - Consultar los participantes inscritos en un taller específico.

* `DELETE /talleres/{tallerId}/participantes/{participanteId}` - Cancelar la inscripción de un participante de un taller sin eliminar ninguna de las dos entidades.

## 🛡️ Control de Errores y Validaciones

La API hace uso de un controlador global de excepciones (`GlobalExceptionHandler`) adaptado a la especificación **RFC 7807 (`ProblemDetail`)**:

* **`404 Not Found`**: Recursos no encontrados (`TallerNotFoundException`, `OrganizadorNotFoundException`, `ParticipanteNotFoundException`).

* **`400 Bad Request`**:

  * Violación de restricciones de validación DTO (`@NotBlank`, `@NotNull`, `@Valid`).

  * Validación personalizada de fechas (`@FechaFinPosteriorAInicio`).

  * Registro de nombres de talleres duplicados (`TallerWasFoundException`).

  * Inscripción previa repetida (`ParticipanteAlreadyRegisteredException`).

## 🏁 Ejecución del Proyecto

1. **Clonar el repositorio:**

   ```
   git clone https://github.com/tu-usuario/culturalia.git
   cd culturalia
   
   ```

2. **Compilar y ejecutar la aplicación:**

   ```
   mvn spring-boot:run
   
   ```

3. **Acceso a la consola de la base de datos H2:**

   * **URL:** `http://localhost:8080/h2-console`

   * **JDBC URL:** `jdbc:h2:mem:culturaliadb`

   * **User:** `sa`

   * **Password:** *(dejar en blanco)*

## 📄 Documentación Interactiva (Swagger UI)

Una vez iniciada la aplicación, se puede acceder a la documentación interactiva de la API e interactuar directamente con todos los endpoints a través de la siguiente dirección:

👉 [**http://localhost:8080/swagger-ui.html**](http://localhost:8080/swagger-ui.html)

## 🧪 Colección de Postman

Se incluye en la raíz del repositorio el archivo `Culturalia.postman_collection.json` con todos los casos de prueba requeridos (peticiones correctas, casos de error 400 y 404, validaciones de fechas y relaciones M:M).