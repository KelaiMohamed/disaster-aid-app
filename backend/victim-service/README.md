# Victim Service

Manages everything on the "I need help" side of the platform: victims' assistance requests, the aid types and skills those requests need, and the locations they're needed at.

## Domain model

- **AssistantRequests** — a victim's request for help; links to a `Location`, a set of `Skills` needed, and a set of `AidType`s (financial, logistical, etc.)
- **Location** — where the assistance is needed
- **Skills** — a skill relevant to fulfilling a request (matched against volunteers' offered skills, on the [volunteer-service](../volunteer-service) side)
- **AidType** — a category of aid (financial, logistical, medical, etc.)

## Endpoints

All routes are prefixed `/victim` and reached through the [Gateway](../gateway-service) at `/victim/**` (requires a valid JWT).

| Resource | Endpoints |
|---|---|
| Assistance requests | `GET /victim/AssistantRequests`, `GET /victim/AssistantRequests/{id}`, `GET /victim/AssistantRequests/user/{userId}`, `POST /victim/AssistantRequests`, `PUT /victim/AssistantRequests/{id}`, `DELETE /victim/AssistantRequests/{id}` |
| Locations | `GET /victim/Locations`, `GET /victim/Locations/{id}`, `POST /victim/Locations`, `PUT /victim/Locations/{id}`, `DELETE /victim/Locations/{id}` |
| Skills | `GET /victim/skills`, `GET /victim/skills/{id}`, `POST /victim/skills`, `PUT /victim/skills/{id}`, `DELETE /victim/skills/{id}` |
| Aid types | `GET /victim/aidtypes`, `GET /victim/aidtypes/{id}`, `POST /victim/aidtypes`, `PUT /victim/aidtypes/{id}`, `DELETE /victim/aidtypes/{id}` |

## Configuration

| Variable | Default | Description |
|---|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/victim` | Postgres connection string |
| `DB_USERNAME` | `postgres` | Postgres username |
| `DB_PASSWORD` | `postgres` | Postgres password |
| `EUREKA_URI` | `http://localhost:8761/eureka/` | Service registry URL |
| `SERVER_PORT` | `8082` | HTTP port |

## Running standalone

```bash
createdb victim   # if it doesn't exist yet
export DB_PASSWORD=your-local-postgres-password
./mvnw spring-boot:run
```

## Architecture

Controllers delegate to a service layer (`Service/`), which owns the data-access and business logic; controllers only handle HTTP concerns (status codes, request/response shaping). This used to be inconsistent with [auth-service](../auth-service) — controllers here called the JPA repositories directly — and has since been aligned to the same pattern.

## Tech

Spring Boot 3.2, Spring Data JPA, PostgreSQL, Lombok, Spring Cloud Netflix Eureka Client, Spring Boot Actuator.
