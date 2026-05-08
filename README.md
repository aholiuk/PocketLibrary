# Pocket Library

A personal book library REST API built with Spring Boot and secured with Keycloak.

Track your reading progress, write reviews, connect with friends and get personalized book recommendations — all through a clean, role-protected REST API.

---

## Features

- **Book management** — add, update, delete books, track reading progress and ratings
- **Reviews** — write and browse book reviews
- **Friends** — connect with other readers
- **Quiz & Recommendations** — answer a quiz and get personalized book suggestions
- **Keycloak Security** — OAuth2 JWT authentication with role-based access control
- **Swagger UI** — interactive API documentation

---

## Tech Stack

| | |
|---|---|
| Java 21 | Spring Boot 3.4.5 |
| Spring Security + OAuth2 | Keycloak 25 |
| PostgreSQL | Spring Data JPA |
| Swagger / OpenAPI 3 | Lombok |

---

## Getting Started

### Prerequisites

- Java 21
- PostgreSQL 15+
- Keycloak 25
- Maven 3.9+

### 1. Database

```sql
CREATE DATABASE pocket_library;
```

Default credentials: `postgres` / `admin` (configurable in `application.yaml`)

### 2. Keycloak

Start Keycloak in dev mode:

```bash
./bin/kc.sh start-dev
```

Then configure:
- Create realm: `PocketLibrary`
- Create client: `pocket-library` (with client authentication)
- Create roles: `read`, `admin`
- Create a test user and assign the `read` role

### 3. Run the App

```bash
./mvnw spring-boot:run
```

- API: `http://localhost:9090`
- Swagger UI: `http://localhost:9090/swagger-ui.html`

---

## Roles

| Role | Access |
|---|---|
| `read` | Browse library, manage own reviews, friends and quiz |
| `admin` | Full access — manage books, view all users, delete |

---

## Tests

```bash
./mvnw test
```

Includes controller tests (`@WebMvcTest`) and database CRUD tests (`@DataJpaTest`).

---

## Project Structure

```
src/main/java/.../pocket_library/
├── auth/           # Keycloak user registration
├── book/           # Book CRUD
├── friend/         # Friend connections
├── quiz/           # Quiz answers
├── recommendation/ # Book recommendations
├── review/         # Book reviews
├── security/       # JWT role converter & security config
├── swagger/        # OpenAPI config
└── user/           # User management
```
