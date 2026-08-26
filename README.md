# 🏢 Meeting Room Booking System (REST API)

![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-blue.svg?logo=kotlin)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1+-brightgreen.svg?logo=spring-boot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-green.svg?logo=spring-security)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg?logo=mysql)
![Redis](https://img.shields.io/badge/Redis-7.x-red.svg?logo=redis)

A robust, scalable, and production-ready REST API built with **Kotlin** and **Spring Boot 3**. 

This repository serves as a comprehensive showcase of modern backend architecture, focusing heavily on **various authentication strategies**, **distributed caching**, and **production observability**. Instead of a monolithic approach, the project was developed using a strict Git Flow, implementing different security and caching architectures in isolated feature branches.

## 🚀 Key Features & Production-Ready Practices

* **Advanced Security Architectures:** Explores everything from Basic Auth to stateless JWT with Redis blacklisting and OAuth2/OpenID Connect.
* **Distributed Caching:** Utilizes Redis for high-performance business data caching (`@Cacheable`) and session management.
* **Centralized Exception Handling:** Implements `@RestControllerAdvice` to provide consistent, client-friendly RFC 7807 JSON error responses.
* **Observability & Tracing:** Configured with SLF4J, Logback, and MDC (Mapped Diagnostic Context) to generate structured, traceable logs (`traceId`) ready for ELK stack integration.
* **Database Versioning:** Safe and predictable database schema migrations using Flyway.
* **External API Integration:** Demonstrates secure server-to-server communication using custom HTTP Clients (RestClient) to fetch external data (OpenWeather API).

## 🛠️ Tech Stack

* **Language:** Kotlin (Java 21)
* **Framework:** Spring Boot 3.x, Spring WebMVC, Spring Data JPA
* **Security:** Spring Security, OAuth2, JJWT (Java JWT)
* **Database:** MySQL
* **Caching & Sessions:** Redis, Spring Session Data Redis, Spring Cache
* **Migrations:** Flyway
* **Logging:** Logback, SLF4J, MDC

---

## 🌿 Feature Branches (The Learning Journey)

This project was built modularly. You can check out the specific branches below to see isolated implementations of different architectural patterns:

### Authentication & Security
* `feat/basic-auth`: Implementation of classic HTTP Basic Authentication.
* `feat/auth-session`: Stateful session management using in-memory JVM sessions.
* `feat/auth-session-redis`: Horizontal scaling of sessions using **Spring Session Data Redis**.
* `feat/auth-jwt`: Stateless authentication using JSON Web Tokens (Access & Refresh tokens).
* `feat/jwt-redis-blacklist`: Advanced JWT security implementing token revocation/blacklisting via Redis upon logout.
* `feat/oauth2` & `feat/openid-auth`: Social login and SSO integration using Google OAuth2 and OpenID Connect.
* `feat/api-keys`: Implementation of custom API Key filters to protect specific endpoints for server-to-server communication.

### Performance & Observability
* `feat/auth-jwt-logback`: Implementation of structured JSON logging for Dev/Prod profiles with MDC `traceId` injection for request tracing.
* `feat/jwt-caching-redis`: Business logic optimization using Spring Cache and Redis to cache frequently accessed data (e.g., available rooms) with `@CacheEvict` invalidation.

---

## ⚙️ Getting Started

### Prerequisites
* Java 21+
* MySQL 8.0+
* Redis (Local or Docker)
* Gradle

### 1. Clone the repository
```bash
git clone [https://github.com/Spaicyyy/RoomBooking.git](https://github.com/Spaicyyy/RoomBooking.git)
cd RoomBooking
