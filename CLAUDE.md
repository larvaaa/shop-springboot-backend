# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

This is a Gradle multi-module project. All commands run from the repo root.

```bash
# Build all modules
./gradlew build

# Build a single module
./gradlew :member-service:build

# Run a specific service
./gradlew :member-service:bootRun

# Run tests for a specific module
./gradlew :member-service:test

# Run a single test class
./gradlew :member-service:test --tests "com.shopping.member.SomeTest"
```

## Architecture Overview

Spring Boot 3 / Kotlin microservices platform using Spring Cloud Netflix stack.

**Service ports:**
| Service | Port |
|---|---|
| eureka-server | 8761 |
| apigateway-service | 8000 |
| member-service | 8082 |
| admin-service | 8081 |

**Request flow:** Client → API Gateway (`:8000`) → Eureka (service discovery) → downstream service

**Startup order:** eureka-server → apigateway-service → business services

### Module Structure

- **`module-common/common-core`** — shared `JwtUtil` (HS256 JWT generation/validation), `ApiResponse` wrapper, `application-core.yml` (JWT config). Imported by all services via `spring.config.import`.
- **`module-common/common-jpa`** — JPA audit config, `BaseEntity`, QueryDSL config, `application-jpa.yml` (Hibernate settings, `ddl-auto: none`).
- **`module-common/common-security`** — reusable Spring Security filter chain (`CustomTokenAuthFilter`, `SecurityConfig`). Services that need JWT-secured endpoints depend on this module instead of rolling their own.
- **`apigateway-service`** — Spring Cloud Gateway with WebFlux reactive security. Validates JWTs via `AuthenticationManager`/`SecurityContextRepository` before routing. CORS configured for `loc.fooddelivery.com:3000`.
- **`member-service`** — OAuth2 Authorization Server (Spring Authorization Server). Issues JWTs on login, manages `Member`/`Role`/`MemberRole` entities, publishes Kafka events.
- **`admin-service`** — Admin back-office (web MVC). Domain split under `domain/` (e.g., `domain/member`, `domain/system`). Uses Kafka for event consumption.
- **`batch-service`** — Spring Batch jobs. Uses `common-core` + `common-jpa`, no web layer.
- **`order-service`**, **`store-service`**, **`delivery-service`** — stubs; depend on `common-core` + `common-jpa` but have minimal implementation.

### Environment Profiles

Services use profile-based config split:
- `application.yml` — base config (port, app name, Kafka, Eureka, profile activation)
- `application-loc.yml` — local DB datasource + cookie domain (`loc.fooddelivery.com`)
- `application-dev.yml` — dev DB datasource

`common-core` and `common-jpa` YAMLs are imported explicitly in each service's `application.yml` via `spring.config.import`.

### Auth Flow

1. Client POSTs credentials to `member-service /login` (bypassed in gateway)
2. `member-service` Spring Authorization Server issues JWT (HS256, signed with `jwt.secret-key` from `application-core.yml`)
3. Gateway's `SecurityContextRepository` extracts and validates the JWT on subsequent requests using `JwtUtil` from `common-core`
4. Validated identity is forwarded to downstream services

### Kafka

Bootstrap server is a fixed external address (`13.125.251.87:9092`). `admin-service` and `member-service` are both producers and consumers using the `test-group` consumer group.

### QueryDSL

`common-jpa` provides `QueryDSLConfig`. Individual services add `kapt` annotation processors in their `build.gradle.kts` to generate Q-classes:
```kotlin
kapt("io.github.openfeign.querydsl:querydsl-apt:6.10.1:jpa")
kapt("jakarta.annotation:jakarta.annotation-api")
kapt("jakarta.persistence:jakarta.persistence-api")
```

### Key Dependencies

- Spring Boot 3.4.2 / Spring Cloud 2024.0.0
- Kotlin 1.9.25 / JVM target 17
- `kotlin-logging-jvm` (`mu.KotlinLogging`) for logging throughout
- `p6spy` for SQL parameter logging (enabled per service in config)
