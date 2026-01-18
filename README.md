# E-Commerce API

[![Java](https://img.shields.io/badge/Java-21-blue)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-green)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9.1-red)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-enabled-blue)](https://www.docker.com/)

---

## Overview

This is a **REST API for an E-Commerce platform**.  

It manages:

- **Users**: registration, authentication, profile, account activation/deactivation  
- **Orders**: create, update, cancel, list  
- **Payments**: create, refund, confirm refund  
- **Product Catalog**: CRUD, search by name, category, price  
- **Bills**: retrieve invoice by order  

The API is fully documented with **Swagger/OpenAPI** and secured with **JWT tokens**.

---

## Technologies

- Java 21  
- Spring Boot 4  
- Spring Security (JWT)  
- Spring Data JPA / Hibernate  
- Maven  
- PostgreSQL (configurable)  
- Swagger UI / OpenAPI 3.01  
- Docker & Docker Compose  

---

## Quick Start

### Clone repository

```bash
git clone https://github.com/<your-username>/ecommerce-api.git
cd ecommerce-api
```

### Build with Maven

```bash
mvn clean package -DskipTests
```

### Run with Docker Compose

```bash
docker-compose up --build
```

- Application runs at **http://localhost:8081**  
- Swagger UI:  
```
http://localhost:8081/swagger-ui/index.html
```

---

## Swagger UI

- Use the **Authorize** button to set JWT:  
```
Bearer <ACCESS_TOKEN>
```

---

## Authentication

| Endpoint | Description |
|----------|-------------|
| `POST /auth/register/user` | Register a new user |
| `POST /auth/register/admin` | Register a new admin (ADMIN only) |
| `POST /auth/login` | Login and get access & refresh tokens |
| `POST /auth/refresh-token` | Refresh access token |

**Example JWT Header in Swagger:**  
```
Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## API Modules & Endpoints

### 1️⃣ Payments

```
POST   /payment/create           → Create payment
POST   /payment/{id}/refund      → Request refund
POST   /payment/{id}/confirm-refund → Confirm refund (ADMIN)
GET    /payment/all              → List payments for authenticated user
```

### 2️⃣ Product Catalog

```
POST   /product/addProduct        → Add product (ADMIN)
PUT    /product/update/{id}       → Update product (ADMIN)
DELETE /product/delete/{id}       → Delete product (ADMIN)
GET    /product/by-productName    → Search by name
GET    /product/category          → Filter by category
GET    /product/by-price          → Filter by price
GET    /product/id/{id}           → Get product by ID
```

### 3️⃣ Orders

```
POST   /order                     → Create order
GET    /order/by-user             → List orders by user
PATCH  /order/cancel              → Cancel order
PUT    /order/update              → Update order
```

### 4️⃣ Bills

```
GET    /bill?orderId=             → Get bill by order
```

### 5️⃣ Users

- **Account actions:** activate, deactivate, suspend, reactivate  
- **Profile updates:** fields, address, profile image  
- **Queries:** by ID, email, username, role, status, creation date

---

## Pagination & Sorting

- `page` → Page number (start at 0)  
- `field` → Field to sort (`name`, `price`, `createdAt`, etc.)  
- `desc` → Descending order (`true`/`false`)  

---

## HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200  | OK / Success |
| 201  | CREATED / Resource created |
| 204  | NO CONTENT / Successful action without body |
| 400  | BAD REQUEST / Invalid request |
| 401  | UNAUTHORIZED / Missing or invalid JWT |
| 403  | FORBIDDEN / Insufficient permissions |
| 404  | NOT FOUND / Resource not found |
| 500  | INTERNAL SERVER ERROR / Unexpected server error |

---

## Example Requests

### Register User

```json
POST /auth/register/user
{
  "name": "John",
  "surname": "Doe",
  "email": "john@example.com",
  "password": "secret123",
  "taxId": "123456789"
}
```

### Login

```json
POST /auth/login
{
  "email": "john@example.com",
  "password": "secret123"
}
```

**Response:**
```json
{
  "accessToken": "<JWT>",
  "refreshToken": "<JWT>",
  "userName": "john@example.com",
  "roles": ["ROLE_USER"]
}
```

---

## Diagrams

### API Modules

```
Users  ---> Orders  ---> Payments ---> Bills
 |                             ^
 |-----------------------------|
Product Catalog
```

- Users can manage account & profile  
- Orders are linked to users  
- Payments linked to orders  
- Bills linked to orders  
- Product Catalog is referenced in Orders  

---

## License

This project is licensed under the MIT License - see [LICENSE](LICENSE) for details.
