# Meeting Room Booking System API

A robust and scalable backend system for managing meeting room reservations. This project serves as a comprehensive implementation of modern authentication mechanisms, caching strategies, and API security best practices.

## Key Features

*   **Multi-Strategy Authentication:** Fully implements and isolates various security protocols, including Basic Auth, Session-based authentication, JWT, OAuth2, OpenID, and API Keys.
*   **Redis Integration:** Leverages Redis for high-performance data caching, centralized stateless session management, and a robust JWT token blacklisting mechanism for secure logouts.
*   **Observability & Reliability:** Features advanced logging using Logback with MDC (Mapped Diagnostic Context) for injecting unique `traceId`s, paired with a global exception handler for standardized JSON error responses.

## Tech Stack

*   **Core:** Kotlin, Java 21, Spring Boot 3
*   **Data Layer:** MySQL, Spring Data JPA, Hibernate, Flyway Database Migrations
*   **Security & Infrastructure:** Spring Security, JJWT, Spring Session, Redis, Spring Cache

## Branch Architecture

This repository is structured to demonstrate iterative feature development. Each branch represents an isolated implementation of a specific architectural pattern:

*   `feat/auth-session`, `feat/auth-jwt`, `feat/basic-auth` - Core authentication flows.
*   `feat/oauth2`, `feat/openid-auth`, `feat/api-keys` - External identity providers and service-to-service API protection.
*   `feat/*-redis`, `feat/*-logback` - Infrastructure improvements including caching, distributed sessions, and advanced request tracing.

---
*Developed by Əvəz Qurbanlı — Software Engineering Student at Karadeniz Technical University.*
