# Gateway Service

The single entry point into the platform. Built on Spring Cloud Gateway (WebFlux/reactive), it routes incoming requests to the right backend service by path, and enforces authentication on protected routes before the request ever reaches a domain service.

## Responsibilities

- Reverse proxy / router: forwards `/auth/**`, `/user/**`, `/victim/**`, `/volunteer/**` to the corresponding service, resolved dynamically via Eureka (`lb://` load-balanced URIs — no hardcoded service addresses)
- Authentication: every route except `/auth/register` and `/auth/token` (see [`RouteValidator`](src/main/java/ma/fstt/filter/RouteValidator.java)) requires a valid `Authorization: Bearer <token>` header, checked by [`AuthenticationFilter`](src/main/java/ma/fstt/filter/AuthenticationFilter.java) against auth-service's signing key
- CORS handling for the frontend

## Routes

| Path prefix | Forwards to | Requires auth |
|---|---|---|
| `/auth/register`, `/auth/token` | auth-service | No |
| `/auth/**` (anything else) | auth-service | Yes |
| `/user/**` | auth-service | Yes |
| `/victim/**` | victim-service | Yes |
| `/volunteer/**` | volunteer-service | Yes |

## Configuration

| Variable | Default | Required | Description |
|---|---|---|---|
| `EUREKA_URI` | `http://localhost:8761/eureka/` | No | Service registry URL, used to resolve the `lb://` route targets above |
| `SERVER_PORT` | `8080` | No | HTTP port |
| `JWT_SECRET` | — | **Yes** | Must match auth-service's `JWT_SECRET` exactly |

## Running standalone

The Gateway is only useful with at least the registry and one domain service also running (it has nothing to route to otherwise) — see the [root README](../../README.md#quick-start) for the full stack. To run just this service:

```bash
export JWT_SECRET="$(openssl rand -base64 32)"   # must match auth-service's
./mvnw spring-boot:run
```

## A note on a bug that used to live here

The `/user/**` route previously had a typo — `fitters:` instead of `filters:` in the YAML config — which meant the `AuthenticationFilter` silently never applied to that route. Any client could hit `/user/**` with no token at all. Fixed; see the root README's "Fixes applied" section for the full list of what else was wrong across the stack.

## Tech

Spring Boot 3.1, Spring Cloud Gateway (WebFlux), Spring Cloud Netflix Eureka Client, [jjwt](https://github.com/jwtk/jjwt), Spring Boot Actuator.
