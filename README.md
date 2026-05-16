# Spring Playground

A prototype and learning playground exploring patterns for a future **production-ready HR platform**.

This repo is where I experiment with Spring Boot, Modulith, JPA, and related tooling before they land in the real product. Code here is intentionally rough, it's about learning the idioms, not shipping features.

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
./gradlew bootRun
```

Spring Boot's Docker Compose integration starts Postgres and the Grafana LGTM stack automatically.
