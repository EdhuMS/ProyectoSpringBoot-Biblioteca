# 📚 Sistema de Gestión de Biblioteca

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Thymeleaf](https://img.shields.io/badge/Frontend-Thymeleaf-lightgrey)
![Bootstrap](https://img.shields.io/badge/UI-Bootstrap_5-purple)

Aplicación web monolítica para la administración integral de una biblioteca. Desarrollada con **Spring Boot** y **Thymeleaf**, enfocada en la arquitectura limpia, seguridad robusta y experiencia de usuario moderna (UX/UI).

---

## 🚀 Características Principales

### 🔐 Seguridad y Acceso
* **Autenticación Robusta:** Sistema de Login con diseño *Glassmorphism*.
* **Roles y Permisos:** * `ADMINISTRADOR`: Acceso total (incluyendo gestión de empleados).
    * `EMPLEADO`: Gestión de libros, socios y préstamos.
* **Protección contra Fuerza Bruta:** Bloqueo automático de cuenta tras 5 intentos fallidos.
* **Seguridad:** Implementación de Spring Security con BCrypt y protección CSRF.

### 📖 Catálogo y Usuarios
* **Gestión de Libros:** CRUD completo con paginación en servidor y búsqueda por Título/Autor.
* **Gestión de Socios:** Directorio de lectores con búsqueda por Nombre/DNI y paginación.
* **Control de Stock:** Validación automática de ejemplares disponibles.

### 🔄 Circulación (Préstamos)
* **Flujo de Préstamo:** Asignación rápida validando stock y existencia de socio.
* **Devoluciones Inteligentes:** * Cálculo automático de multas por días de retraso.
    * Generación de **Ticket de Devolución / Comprobante de Pago**.
* **Reportes:** Vista rápida de préstamos vencidos.

### 🎨 Interfaz de Usuario (UI/UX)
* **Diseño Moderno:** Header con gradientes, íconos de Bootstrap y tablas responsivas.
* **Feedback al Usuario:** Modales de confirmación reutilizables y alertas (Toast) para acciones exitosas o errores.
* **Navegación Intuitiva:** Menús categorizados por "Catálogo" y "Personas".

---

## 🛠️ Arquitectura y Calidad de Código

Este proyecto ha sido refactorizado siguiendo estándares de la industria:
1.  **Inyección de Dependencias:** Uso estricto de **Inyección por Constructor** (`@RequiredArgsConstructor`) eliminando `@Autowired` en campos.
2.  **Manejo Global de Excepciones:** Uso de `@ControllerAdvice` para centralizar errores y evitar bloques `try-catch` en controladores.
3.  **Patrón DTO:** Uso de Data Transfer Objects (`PrestamoDTO`, `UsuarioDTO`) para evitar vulnerabilidades de *Over-Posting*.
4.  **Principios SOLID:** Separación clara de responsabilidades entre Controladores, Servicios y Repositorios.

---

## ⚙️ Configuración e Instalación

### Prerrequisitos
* JDK 17 o superior.
* Maven.
* MySQL Server.

### Pasos para ejecutar
1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/tu-usuario/sistemagestionbiblioteca.git](https://github.com/tu-usuario/sistemagestionbiblioteca.git)
    ```
2.  **Configurar Base de Datos:**
    Asegúrate de tener creada una base de datos vacía llamada `biblioteca_db` en MySQL.
    
3.  **Configurar Credenciales:**
    Edita el archivo `src/main/resources/application.properties` si tu usuario/pass de MySQL son diferentes:
    ```properties
    spring.datasource.username=root
    spring.datasource.password=tu_contraseña
    ```

4.  **Ejecutar la aplicación:**
    ```bash
    mvn spring-boot:run
    ```
    *Nota: Al iniciar, la aplicación cargará automáticamente datos de prueba (libros, socios y usuarios).*

---

## 👤 Usuarios de Prueba (DataInitializer)

El sistema crea automáticamente estos usuarios al arrancar por primera vez:

| Rol | Usuario | Contraseña | Permisos |
| :--- | :--- | :--- | :--- |
| **Administrador** | `admin` | `Admin@123` | Control Total + Gestión de Usuarios |
| **Empleado** | `pepe` | `Pepe@123` | Catálogo, Socios y Préstamos |

---

## 📸 Capturas de Pantalla
*(Puedes agregar aquí imágenes de tu Login moderno o del Dashboard)*

---

**Desarrollado con ❤️ usando Spring Boot**