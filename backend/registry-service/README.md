# Registry Service

A Netflix Eureka server — the service discovery mechanism that lets every other service find every other service by name instead of a hardcoded address. Every domain service and the Gateway register themselves here on startup; the Gateway resolves its `lb://` routes through it.

This service doesn't register itself with anything (`eureka.client.register-with-eureka: false`) and doesn't fetch a registry of its own (`eureka.client.fetch-registry: false`) — it *is* the registry.

## Dashboard

Once running, the Eureka dashboard is at `http://localhost:8761` — useful for confirming which services are actually up and registered, especially when debugging the stack locally.

> The dashboard has no authentication in front of it. That's fine for local development and for a cluster-internal service (nothing outside the cluster should be able to reach it), but don't expose this port publicly.

## Configuration

| Variable | Default | Description |
|---|---|---|
| `SERVER_PORT` | `8761` | HTTP port |

That's it — this service has no database, no downstream dependencies, and no secrets. It's the first thing that needs to be healthy before anything else can register.

## Running standalone

```bash
./mvnw spring-boot:run
```

Then visit `http://localhost:8761`.

## Tech

Spring Boot 3.1, Spring Cloud Netflix Eureka Server, Spring Boot Actuator.
