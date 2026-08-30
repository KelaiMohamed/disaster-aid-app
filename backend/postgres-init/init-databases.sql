-- Each backend service owns its own database, all served by this single
-- local Postgres instance for convenience in docker-compose. This mirrors
-- the original per-service application.yml configuration (one Postgres
-- server, one database per service) rather than a shared database.
CREATE DATABASE auth;
CREATE DATABASE victim;
CREATE DATABASE volunteer;
