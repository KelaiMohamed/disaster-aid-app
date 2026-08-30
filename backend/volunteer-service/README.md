# Volunteer Service

Manages everything on the "I want to help" side of the platform: volunteers' skills, the assistance offers they make, and donations.

## Domain model

- **Skill** — a skill a volunteer offers (matched against victims' requested skills, on the [victim-service](../victim-service) side)
- **AssistanceOffer** — a volunteer's offer to help with a specific assistance request
- **Donation** — a monetary or material donation
- **AidType** — a category of aid a volunteer can help provide

## Endpoints

All routes are prefixed `/volunteer` and reached through the [Gateway](../gateway-service) at `/volunteer/**` (requires a valid JWT).

| Resource | Endpoints |
|---|---|
| Skills | `GET /volunteer/skills`, `GET /volunteer/skills/{id}`, `GET /volunteer/skills/user/{userId}`, `POST /volunteer/skills`, `PUT /volunteer/skills/{id}`, `DELETE /volunteer/skills/{id}` |
| Assistance offers | `GET /volunteer/assistanceoffers`, `GET /volunteer/assistanceoffers/{id}`, `GET /volunteer/assistanceoffers/user/{userId}`, `GET /volunteer/assistanceoffers/request/{assistanceRequestId}`, `POST /volunteer/assistanceoffers`, `PUT /volunteer/assistanceoffers/{id}`, `DELETE /volunteer/assistanceoffers/{id}` |
| Donations | `GET /volunteer/donations`, `GET /volunteer/donations/{id}`, `POST /volunteer/donations`, `PUT /volunteer/donations/{id}`, `DELETE /volunteer/donations/{id}` |
| Aid types | `GET /volunteer/aidtypes`, `GET /volunteer/aidtypes/{id}`, `POST /volunteer/aidtypes`, `PUT /volunteer/aidtypes/{id}`, `DELETE /volunteer/aidtypes/{id}` |

## Configuration

| Variable | Default | Description |
|---|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/volunteer` | Postgres connection string |
| `DB_USERNAME` | `postgres` | Postgres username |
| `DB_PASSWORD` | `postgres` | Postgres password |
| `EUREKA_URI` | `http://localhost:8761/eureka/` | Service registry URL |
| `SERVER_PORT` | `8081` | HTTP port |

## Running standalone

```bash
createdb volunteer   # if it doesn't exist yet
export DB_PASSWORD=your-local-postgres-password
./mvnw spring-boot:run
```

## Architecture

Controllers delegate to a service layer (`service/`), which owns the data-access and business logic; controllers only handle HTTP concerns (status codes, request/response shaping). This used to be inconsistent with [auth-service](../auth-service) — controllers here called the JPA repositories directly — and has since been aligned to the same pattern.

## Tech

Spring Boot 3.1, Spring Data JPA, PostgreSQL, Lombok, Spring Cloud Netflix Eureka Client, Spring Boot Actuator.
