# 🏫 Sistema de Gestión Escolar — Colegio Bernardo O'Higgins

Sistema web fullstack para la gestión académica, de asistencia y comunicaciones de un colegio. Construido con arquitectura de microservicios en Spring Boot y frontend en React + Vite.

---

## 🧱 Arquitectura

```
Frontend (React + Vite)
        │
        ▼
  API Gateway :9090  ──── JWT Auth
        │
   ┌────┴──────────────┐
   │                   │                   │
Servicio           Servicio           Servicio
Académico          Asistencia      Comunicaciones
  :8081              :8082              :8083
   │                   │                   │
 MySQL              MySQL            MongoDB
                       │                   │
                   RabbitMQ ◄─────────────┘
                   (eventos de anotaciones)
```

---

## 📦 Microservicios

| Servicio | Puerto | Base de datos | Descripción |
|---|---|---|---|
| `api-gateway` | 9090 | — | Enrutamiento y autenticación JWT |
| `servicio-academico` | 8081 | MySQL | Estudiantes, profesores, notas, asignaturas |
| `servicio-asistencia` | 8082 | MySQL | Asistencia y anotaciones |
| `servicio-comunicaciones` | 8083 | MongoDB | Mensajes y notificaciones |

### API Gateway
Punto de entrada único. Autentica con JWT y redirige las peticiones a cada microservicio según el prefijo de ruta:

| Prefijo | Destino |
|---|---|
| `/academico/**` | servicio-academico |
| `/asistencia/**` | servicio-asistencia |
| `/comunicaciones/**` | servicio-comunicaciones |

### Servicio Académico
Gestiona el núcleo del colegio: estudiantes, apoderados, profesores, asignaturas, evaluaciones y notas.

### Servicio Asistencia
Registra asistencia diaria y anotaciones de conducta. Publica eventos via **RabbitMQ** cuando se crea una anotación.

### Servicio Comunicaciones
Consume los eventos de RabbitMQ y genera notificaciones automáticas para apoderados. También gestiona mensajes internos.

---

## 🖥️ Frontend

Aplicación React con vistas diferenciadas por rol:

| Rol | Páginas |
|---|---|
| **Admin** | Dashboard, gestión de usuarios |
| **UTP** | Dashboard, libros, aprobaciones, usuarios |
| **Profesor** | Dashboard, mis cursos, detalle de curso, registrar notas y anotaciones |
| **Alumno** | Dashboard, notas, asistencia, anotaciones |
| **Apoderado** | Dashboard, ficha, observaciones, notificaciones |

---

## 🚀 Levantar el proyecto

### Requisitos previos
- Docker y Docker Compose instalados
- Git

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/Unluccky/colegio-bernardo-ohiggins.git
cd colegio-bernardo-ohiggins

# 2. Levantar todos los servicios con Docker Compose
docker compose up --build
```

Eso levanta automáticamente:
- Las 3 bases de datos (MySQL x2, MongoDB)
- RabbitMQ
- Los 3 microservicios + API Gateway
- SonarQube (calidad de código)

### Levantar el frontend (desarrollo)
```bash
cd frontend
npm install
npm run dev
```

El frontend estará disponible en `http://localhost:5173`.

---

## 🌐 Puertos disponibles

| Servicio | URL |
|---|---|
| Frontend | http://localhost:5173 |
| API Gateway | http://localhost:9090 |
| Servicio Académico | http://localhost:8081 |
| Servicio Asistencia | http://localhost:8082 |
| Servicio Comunicaciones | http://localhost:8083 |
| RabbitMQ (panel) | http://localhost:15672 |
| SonarQube | http://localhost:9000 |

**Credenciales RabbitMQ:** `admin` / `admin`  
**Credenciales SonarQube (primera vez):** `admin` / `admin`

---

## 🛠️ Tecnologías

**Backend**
- Java 17 + Spring Boot 3
- Spring Cloud Gateway
- Spring Security + JWT
- Spring Data JPA / Spring Data MongoDB
- OpenFeign (comunicación entre servicios)
- RabbitMQ (mensajería asíncrona)
- MySQL 8 / MongoDB 6
- Docker + Docker Compose
- SonarQube (análisis de calidad)

**Frontend**
- React 18 + Vite
- React Router
- Context API (autenticación)

---

## 🌿 Flujo de trabajo Git

El proyecto sigue la estrategia **Git Flow**:

```
feature/backend/nombre-apellido  ──┐
feature/frontend/nombre-apellido ──┤──► develop ──► release/vX.X ──► main
hotfix/descripcion               ──┘                                    │
                                                                         └──► tag vX.X
```

Convención de ramas:

| Prefijo | Uso |
|---|---|
| `feature/backend/` | Nuevas funcionalidades backend |
| `feature/frontend/` | Nuevas funcionalidades frontend |
| `fix/` | Corrección de errores |
| `hotfix/` | Parches urgentes en producción |
| `docs/` | Documentación |
| `refactor/` | Mejoras internas sin cambiar comportamiento |

---

## 📁 Estructura del repositorio

```
colegio-bernardo-ohiggins/
├── api-gateway/               # Spring Cloud Gateway + JWT
├── servicio-academico/        # Gestión académica
├── servicio-asistencia/       # Asistencia y anotaciones
├── servicio-comunicaciones/   # Mensajes y notificaciones
├── frontend/                  # React + Vite
└── docker-compose.yml         # Orquestación completa
```
