# Springdeed

A prototype indeed clone and learning playground exploring patterns for a future **production-ready job marketplace platform**.

This repo is where I experiment with Spring Boot, Modulith, JPA, and related tooling before they land in the real product. Code here is intentionally rough, it's about learning the idioms, not shipping features.

## Layout

This is a monorepo:

- `backend/` — the Spring Boot app (Java 25, Gradle Kotlin DSL)
- `frontend/` — React app (not scaffolded yet)
- `services/` — Docker Compose stack for local infra (Postgres, Keycloak, Grafana LGTM) and its `.env`

## Stack

- Java 25
- Spring Boot 4.x
- Spring Modulith
- Spring Data JPA + Postgres
- Flyway
- Gradle (Kotlin DSL)
- Docker Compose for local infra

## Running it

```bash
cd backend
./gradlew bootRun
```

Spring Boot's Docker Compose integration starts Postgres, Keycloak, and the Grafana LGTM stack automatically from `services/compose.yaml`.
