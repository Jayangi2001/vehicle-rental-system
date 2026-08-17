## 👤 User Service (Gateway + Authentication)

**Owner:** H.C Jayangi (ITBIN-2313-0125)
**Role:** Gateway Lead — Authentication, API Gateway routing, CORS, Rate Limiting

### Overview
The User Service handles account registration, login, and JWT token issuance. It also
acts as the single API Gateway for the whole system — the client only ever talks to
this service, which forwards authenticated requests on to the Vehicle Service and the
Rental & Payment Service.

### Tech Stack
- Spring Boot 3.3.4 (Java 17)
- Spring Security + JWT (jjwt)
- MongoDB (Spring Data MongoDB)
- Bucket4j (rate limiting)
- springdoc-openapi (Swagger UI)

### Prerequisites
- Java 17+
- Maven
- Docker Desktop (for MongoDB)

### Running Locally

1. Start MongoDB:
```bash
   docker run -d -p 27017:27017 --name mongodb mongo:7
```
   (If the container already exists: `docker start mongodb`)

2. From the `user-service` folder, run:
```bash
   mvn clean spring-boot:run
```

3. The service starts on **http://localhost:8080**

### Swagger UI

http://localhost:8080/swagger-ui/index.html

All endpoints (`/auth/register`, `/auth/login`, `/auth/me`, and the Gateway routes)
are documented here. Use the **Authorize** button to attach a JWT and test protected
endpoints directly from the browser.

### Endpoints

| Method | Endpoint         | Description                              | Auth Required |
|--------|------------------|-------------------------------------------|----------------|
| POST   | `/auth/register` | Register a new user account               | No             |
| POST   | `/auth/login`    | Authenticate and receive a JWT             | No             |
| GET    | `/auth/me`       | Get the current authenticated user's profile | Yes         |
| ANY    | `/vehicles/**`   | Forwarded to Vehicle Service (port 8082)   | Yes            |
| ANY    | `/rentals/**`    | Forwarded to Rental & Payment Service (port 8083) | Yes     |
| ANY    | `/payments/**`   | Forwarded to Rental & Payment Service (port 8083) | Yes     |

### Authentication Format (JWT)

This service does **not** use a static API key — it issues and validates JWTs.

- **Header name:** `Authorization`
- **Format:** `Bearer <token>`
- **How to get a token:** call `POST /auth/login` with a registered email/password;
  the response contains an `accessToken` valid for 1 hour.

**Test credentials (example — register this first):**
```json
{
  "name": "John Doe",
  "email": "john@test.com",
  "password": "pass123"
}
```

**Login response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600000
}
```

**Using the token on protected requests:**

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...


### Rate Limiting
20 requests per client IP, refilling every 60 seconds (Bucket4j token-bucket).
Exceeding the limit returns `429 Too Many Requests`.

### CORS
Configured centrally in this service since it also acts as the Gateway — allows
`GET, POST, PUT, DELETE, OPTIONS` and the `Authorization`, `Content-Type`, `X-API-KEY`
headers.

### Environment Variables (docker-compose)

| Variable | Value | Purpose |
|---|---|---|
| `SPRING_DATA_MONGODB_URI` | `mongodb://mongodb:27017/userdb` | MongoDB connection |
| `services.vehicle-service.url` | `http://vehicle-service:8082` | Gateway routing target |
| `services.rental-payment-service.url` | `http://rental-payment-service:8083` | Gateway routing target |
| `jwt.secret` | *(set in application.yml)* | JWT signing key |
| `jwt.expiration-ms` | `3600000` | Token validity (1 hour) |
