# 🏢 Meeting Room Booking System (REST API)

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

A robust, scalable, and secure RESTful API for managing meeting room reservations. Built with **Kotlin** and **Spring Boot**, this project demonstrates industry-standard backend development practices, including advanced security mechanisms, distributed caching, and centralized logging.

## ✨ Key Features

* **Advanced Authentication & Authorization:**
  * JWT-based authentication (Access & Refresh tokens).
  * **Redis Token Blacklisting** for secure and immediate user logout.
  * Role-Based Access Control (RBAC).
* **High-Performance Caching:** 
  * Integration with **Spring Data Redis** to cache frequent business queries (e.g., room lists) and automatically evict stale data upon updates (`@Cacheable`, `@CacheEvict`).
* **Production-Ready Logging:** 
  * Custom `MdcFilter` injecting `traceId` and user context into all logs.
  * Configured **Logback** with separate profiles: colorful console output for `dev` and rolling JSON files for `prod` (ready for ELK stack integration).
* **Robust Error Handling:** 
  * Centralized `@RestControllerAdvice` catching both business and framework-level exceptions.
  * Unified JSON error response format containing timestamps, HTTP statuses, and contextual `traceId`.
* **Database Version Control:** 
  * **Flyway** migration scripts ensuring consistent schema initialization and updates across environments.

## 🛠️ Tech Stack

* **Language:** Kotlin, Java 21
* **Framework:** Spring Boot 3.x / 4.x
* **Database:** MySQL (Relational), Spring Data JPA, Hibernate
* **In-Memory Store / Cache:** Redis, Spring Session
* **Security:** Spring Security, io.jsonwebtoken (JJWT)
* **Build Tool:** Gradle (Kotlin DSL)

## 🌿 Git Flow & Branching Strategy

This repository serves as a portfolio showcase of various architectural approaches. While the `main` branch contains the ultimate, production-ready version of the API, several feature branches explore different authentication patterns:
* `feat/auth-jwt` / `feat/jwt-redis-blacklist` - JWT tokens with Redis state management.
* `feat/oauth2` / `feat/openid-auth` - Third-party identity provider integrations.
* `feat/auth-api-key` - Server-to-server API key validation.
* `feat/auth-session-redis` - Stateful distributed sessions via Spring Session Redis.

## 🚀 Getting Started

### Prerequisites
* Java 21+
* MySQL Server (running on default port 3306)
* Redis Server (running on default port 6379)

### 1. Database Setup
Create a local MySQL database:
```sql
CREATE DATABASE booking_jwt;
