# Frontend

The public-facing web app — victims request aid, volunteers browse requests and offer help, all talking to the backend exclusively through the [Gateway](../backend/gateway-service).

## Structure

```
src/app/
├── pages/          # routed pages: home, login, signup, about, victim/*, volunteer/*
├── components/      # shared, non-routed UI (nav bar, hero, footer, ...)
├── services/         # HTTP clients talking to the backend via the Gateway
└── interfaces/       # TypeScript types mirroring the backend's DTOs
```

**Routing** (see `src/app/app-routing.module.ts`): `/login`, `/signup`, `/about`, `/` (home, requires auth), `/victim`, and `/volunteer` with child routes for skills, assistance offers, and making an offer on a specific request.

Auth is handled with a JWT stored in `localStorage`, attached as an `Authorization: Bearer` header on requests, and checked by `AuthGuardService` before entering protected routes.

## Configuration

The Angular app is a static SPA — there's no server-side process to read environment variables from at runtime, so two values are baked in **at Docker build time** instead:

| Build arg | Default | Description |
|---|---|---|
| `GATEWAY_URL` | `http://localhost:8080` | Backend API base URL |
| `GOOGLE_MAPS_API_KEY` | *(empty)* | Used by the location picker on victim/volunteer forms. Get one at the [Google Cloud Console](https://console.cloud.google.com/google/maps-apis) and restrict it to your domain — leaving it empty just means the map widget won't load, everything else works fine. |

See [`Dockerfile`](Dockerfile) for exactly how these get substituted in.

> **Local `ng serve` development** uses `src/environments/environment.development.ts` instead, which already points at `http://localhost:8080` — no build args needed for that.

## Running standalone

**With Docker** (production-style build, served by nginx):
```bash
docker build -t disaster-aid-frontend --build-arg GATEWAY_URL=http://localhost:8080 .
docker run -p 4200:80 disaster-aid-frontend
```

**With the Angular dev server** (hot reload, requires Node 20):
```bash
npm install --legacy-peer-deps
npm start
```
Then visit `http://localhost:4200`. You'll need the Gateway (and whatever it routes to) actually running for anything beyond the static pages to work — see the [root README](../README.md#quick-start).

## Known gaps

- The `/victim` route has no child routes wired up yet (`children: []` in the router config) — victim-facing pages beyond the top-level one aren't reachable through routing yet, unlike the volunteer side which is fully wired.
- Volunteer pages call `HttpClient` directly inside components instead of going through a dedicated Angular service, unlike the victim pages (`services/victim/*.service.ts`). Not broken, just inconsistent — worth unifying if this grows.
- `package.json` has both `@agm/core` and `@angular/google-maps` installed — two different Google Maps integration libraries. Looks like a leftover from a migration between the two; only one should be needed.

## Tech

Angular 16, Tailwind CSS + DaisyUI, RxJS, Font Awesome. Served in production by nginx (see `Dockerfile` / `nginx.conf`).
