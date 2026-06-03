# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
mvnw.cmd clean install

# Run
mvnw.cmd spring-boot:run

# Test
mvnw.cmd test

# Single test class
mvnw.cmd test -Dtest=SimpleServiceApplicationTests
```

Requires PostgreSQL running locally on port 5432 (database: `postgres`, user: `postgres`, pass: `postgres`). Swagger UI available at `/swagger-ui.html` once running.

## Architecture

Accommodation booking REST API (used by an Android app via Retrofit). Strict 3-layer architecture:

```
Controller → ServiceImpl (via Service interface) → Repository → PostgreSQL
```

**Request flow:** Controller validates `@RequestBody` with `@Valid`, delegates to `ServiceImpl`, which calls `JpaRepository`, then uses a `Mapper` to convert the entity into a response DTO.

### Authentication

JWT-based stateless auth (Spring Security 6 + JJWT 0.12).

- `POST /api/auth/register` and `POST /api/auth/login` return `AuthResponse` with a Bearer token.
- Protected routes require `Authorization: Bearer <token>` header.
- `security/` package: `JwtUtil`, `JwtAuthenticationFilter`, `UserDetailsServiceImpl`, `SecurityConfig`.
- Public GET routes: properties, rooms, reviews. Admin-only: POST/PUT/DELETE on properties and rooms.

### Packages

| Package | Role |
|---|---|
| `controllers/` | REST endpoints at `/api/{resource}`. CRUD operations: POST, GET, PUT, DELETE. |
| `services/` | Interfaces defining business contracts. |
| `services/impl/` | `@Service @Transactional` implementations. Throw `ResourceNotFoundException` when an entity is not found. |
| `repository/` | `JpaRepository<Entity, Long>` interfaces. Custom queries use `@Query`. |
| `mapper/` | Manual entity → response DTO conversion using builder pattern. |
| `dto/request/` | Input DTOs with Jakarta Validation (`@NotBlank`, `@Email`, `@Min`/`@Max`, `@Size`). |
| `dto/response/` | Output DTOs including `ErrorResponse` and `AuthResponse`. |
| `models/` | JPA entities with Lombok (`@Getter`/`@Setter`/`@Builder`). |
| `exceptions/` | `ResourceNotFoundException` (runtime), `GlobalExceptionHandler` (`@RestControllerAdvice`). |
| `security/` | JWT filter chain and Spring Security configuration. |

### Domain entities

`Property` (HOTEL/APARTMENT/HOUSE/VILLA) → `Room` → `Order` (booking); `Review`; `User` (auth + guest). Enums: `RoomType`, `RoomStatus`, `OrderStatus`, `PropertyType`, `Role`.

### Key patterns

- Every service impl has a private `getEntityOrThrow(id)` helper.
- `currentUser()` helpers in `OrderServiceImpl` and `ReviewServiceImpl` resolve the authenticated user from `SecurityContextHolder`.
- `Order.totalAmount` is calculated server-side: `room.pricePerNight × nights`. Not provided by the client.
- Room availability is checked via `OrderRepository.isRoomBooked` before confirming a booking.
- Property search: `GET /api/properties/search?city=...&type=...` (both params optional).
- Hibernate DDL is `update` — schema changes auto-apply on startup.
- SQL logging is enabled (`show-sql: true`, `format_sql: true`) — expected in dev output.
