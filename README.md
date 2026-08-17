## 👤 User Service (Gateway + Authentication)

- **Owner:** H.C Jayangi (ITBIN-2313-0125)
- **Role:** Gateway Lead — Authentication, API Gateway routing, CORS, Rate Limiting

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


---

## 👤 Rental & Payment Service

- **Owner:** S.M.K.S. De Silva (ITBIN-2313-0020)

The **Rental & Payment Service** is a microservice of the Vehicle Rental Management System responsible for managing vehicle rentals and processing customer payments. It provides RESTful APIs for creating rental records, retrieving rental details, processing payments, and viewing payment history.

### Technologies Used

- Java 21
- Spring Boot 4.0.7
- Spring Web MVC
- Spring Data MongoDB
- MongoDB
- Spring Security
- SpringDoc OpenAPI / Swagger
- Maven
- Lombok
- Docker

### Service Configuration

- **Service Name:** Rental Payment Service
- **Port:** 8083
- **Database:** MongoDB
- **Database Name:** `vehicle_rental_db`
- **Authentication:** API Key (`X-API-KEY`)

### Main Responsibilities

The service provides the following main functionalities:

1. **Vehicle Rental Management**
   - Create a new vehicle rental.
   - Store customer, vehicle, rental dates, and total rental amount.
   - Maintain rental status such as `PENDING`, `CONFIRMED`, `ONGOING`, `COMPLETED`, and `CANCELLED`.
   - Retrieve rental details using the rental ID.

2. **Payment Management**
   - Process payments related to vehicle rentals.
   - Store payment information including rental ID, user ID, amount, payment method, and payment status.
   - Support payment methods such as `CARD`, `CASH`, and `ONLINE`.
   - Maintain payment statuses such as `SUCCESS`, `FAILED`, and `PENDING`.
   - Retrieve payment history for a specific user.

### API Endpoints

#### Rental Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/rentals` | Create a new vehicle rental |
| GET | `/rentals/{id}` | Retrieve rental details by ID |

#### Payment Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/payments/process` | Process a rental payment |
| GET | `/payments/history/{userId}` | Retrieve payment history of a user |

### Payment Request Example

```json
{
  "rentalId": "rental123",
  "userId": "user123",
  "amount": 15000.00,
  "paymentMethod": "CARD"
}
```

### Payment Response Example

```json
{
  "id": "payment123",
  "rentalId": "rental123",
  "userId": "user123",
  "amount": 15000.00,
  "paymentMethod": "CARD",
  "paymentStatus": "SUCCESS",
  "paymentDate": "2026-08-17T10:30:00"
}
```

### Rental Request Example

```json
{
  "userId": "user123",
  "vehicleId": "vehicle456",
  "startDate": "2026-08-20T09:00:00",
  "endDate": "2026-08-22T18:00:00",
  "totalAmount": 25000.00
}
```

### Security

The service uses **API Key authentication** to protect its REST API endpoints. Clients must provide a valid API key through the `X-API-KEY` request header.

Example:

```text
X-API-KEY: RENTAL-PAYMENT-SECRET-KEY-2026
```

Requests without a valid API key receive a `401 Unauthorized` response.

Swagger UI and API documentation endpoints are excluded from API key authentication to allow developers to explore and test the API.

### Database

MongoDB is used as the database for storing rental and payment information.

The service uses the following main collections:

- `rentals` – Stores vehicle rental information.
- `payments` – Stores payment transaction information.

### Project Structure

```text
rental-payment-service/
├── src/
│   ├── main/
│   │   ├── java/com/vehicle/rentalpayment/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   └── service/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── Dockerfile
├── pom.xml
└── mvnw
```

### API Documentation

The service uses **SpringDoc OpenAPI** to provide interactive API documentation.

Swagger UI:

```text
http://localhost:8083/swagger-ui.html
```

OpenAPI documentation:

```text
http://localhost:8083/api-docs
```

### Running the Service

Make sure MongoDB is running on port `27017`.

Navigate to the `rental-payment-service` directory and run:

```bash
./mvnw spring-boot:run
```

For Windows:

```bash
mvnw.cmd spring-boot:run
```

The service will start on:

```text
http://localhost:8083
```

### Docker Support

The service includes a Dockerfile and can be containerized as part of the Vehicle Rental Management System.

The service communicates with MongoDB and other system components through the configured microservice architecture.

### Key Benefits

- Provides a separate and independently deployable rental and payment service.
- Uses RESTful APIs for communication with other services.
- Stores rental and payment data using MongoDB.
- Secures API access using API Key authentication.
- Provides Swagger/OpenAPI documentation for easy API testing.
- Supports Docker-based deployment.
- Separates rental and payment responsibilities from other vehicle rental system components.

