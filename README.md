# Finance Tracker Backend

Spring Boot backend for the Finance Tracker application.

---

# Tech Stack

- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- MySQL
- Maven

---

# Features

- JWT Authentication
- User Registration & Login
- Categories CRUD
- Transactions CRUD
- Budgets CRUD
- Dashboard Summary API
- Validation & Exception Handling

---

# Project Structure

```bash
src/main/java/com/financetracker/
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── security/
├── config/
└── exception/

Database Setup

Create Database
CREATE DATABASE finance_tracker;

Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_tracker
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
Install Dependencies
mvn clean install
Run Backend
mvn spring-boot:run

Backend runs on:
http://localhost:8080

Authentication
JWT authentication is used.

Public routes:
POST /api/auth/register
POST /api/auth/login

Protected routes:
Categories
Transactions
Budgets
Dashboard
API Endpoints

Auth
POST /api/auth/register
POST /api/auth/login
GET /api/auth/me

Categories
GET /api/categories
POST /api/categories
PUT /api/categories/{id}
DELETE /api/categories/{id}

Transactions
GET /api/transactions
POST /api/transactions
PUT /api/transactions/{id}
DELETE /api/transactions/{id}

Budgets
GET /api/budgets
POST /api/budgets
PUT /api/budgets/{id}
DELETE /api/budgets/{id}

Dashboard
GET /api/dashboard/summary

Validation
Email validation
Required field validation
Positive amount validation
JWT validation

Future Improvements
PDF export
Email notifications
Multi-currency support
AI analytics

Author
Ulindu Dakshitha
