# Auth Service

Handles user registration, login, and JWT issuance/validation for the whole platform. Every other backend service trusts a token that was signed here (and checked at the [Gateway](../gateway-service)) rather than doing its own authentication.

## Responsibilities

- User registration and credential storage (passwords hashed via Spring Security's `PasswordEncoder`)
- Login → issues a signed JWT (HS256)
- Token validation (used by the Gateway's `AuthenticationFilter` on every request to a protected route)
- Basic user lookup

## Endpoints

| Method | Path | Auth required | Description |
|---|---|---|---|
| `POST` | `/auth/register` | No | Create a new user account |
| `POST` | `/auth/token` | No | Log in; returns a JWT |
| `GET` | `/auth/validate` | No* | Validates a token (called internally by the Gateway) |
| `GET` | `/user` | Yes | List users |
| `GET` | `/user/{id}` | Yes | Get a single user |

\* `/auth/validate` doesn't require a bearer token itself (it's what *checks* one), but it's only meant to be called by the Gateway, not exposed to end users directly.

## Configuration

All configuration is in [`src/main/resources/application.yml`](src/main/resources/application.yml), driven entirely by environment variables (no secrets or environment-specific values are hardcoded):

| Variable | Default | Required | Description |
|---|---|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/auth` | No | Postgres connection string |
| `DB_USERNAME` | `postgres` | No | Postgres username |
| `DB_PASSWORD` | `postgres` | No | Postgres password |
| `EUREKA_URI` | `http://localhost:8761/eureka/` | No | Service registry URL |
| `SERVER_PORT` | `9898` | No | HTTP port |
| `JWT_SECRET` | — | **Yes** | HS256 signing key. Must be identical to gateway-service's `JWT_SECRET` — both sign/verify with the same symmetric key. Generate one with `openssl rand -base64 32`. |

## Running standalone

You'll generally want the whole stack up together (see the [root README](../../README.md#quick-start)), but to run just this service against a local Postgres:

```bash
createdb auth   # if it doesn't exist yet
export JWT_SECRET="$(openssl rand -base64 32)"
export DB_PASSWORD=your-local-postgres-password
./mvnw spring-boot:run
```

It'll register itself with Eureka at `http://localhost:8761/eureka/` if that's running, but will start fine without it — you just won't be reachable through the Gateway until it is.

## Tech

Spring Boot 3.1, Spring Data JPA, Spring Security, [jjwt](https://github.com/jwtk/jjwt) for token signing, PostgreSQL, Spring Cloud Netflix Eureka Client, Spring Boot Actuator (health checks at `/actuator/health`).
