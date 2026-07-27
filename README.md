<h1 align="center">
  🚀 WorkForce
</h1>

<h3 align="center">
  Enterprise Employee Management System
</h3>

<p align="center">
A production-ready <strong>Employee Management System</strong> built with <strong>Java 21</strong>, <strong>Spring Boot</strong>, <strong>Spring Security</strong>, <strong>JWT Authentication</strong>, <strong>MySQL</strong>, <strong>Redis</strong>, and <strong>Docker</strong>. The application provides secure authentication, role-based access control (RBAC), employee and department management, RESTful APIs, Redis caching, and a scalable layered architecture following enterprise backend development best practices.
</p>

<p align="center">

<img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" />
<img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot" />
<img src="https://img.shields.io/badge/Spring_Security-6.x-6DB33F?style=for-the-badge&logo=springsecurity" />
<img src="https://img.shields.io/badge/JWT-Authentication-black?style=for-the-badge&logo=jsonwebtokens" />

<br>

<img src="https://img.shields.io/badge/MySQL-Database-4479A1?style=for-the-badge&logo=mysql" />
<img src="https://img.shields.io/badge/Redis-Cache-DC382D?style=for-the-badge&logo=redis" />
<img src="https://img.shields.io/badge/Docker-Container-2496ED?style=for-the-badge&logo=docker" />
<img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven" />

<br>

<img src="https://img.shields.io/badge/License-MIT-blue?style=for-the-badge" />
<img src="https://img.shields.io/badge/Maintained-Yes-success?style=for-the-badge" />

</p>

---

# 📚 Table of Contents

- About the Project
- Features
- Tech Stack
- Project Architecture
- Project Structure
- Getting Started
- Environment Variables
- Authentication Flow
- API Endpoints
- Redis Cache
- Docker Setup
- Screenshots
- Future Improvements
- Author

---

# 📖 About the Project

The **Employee Management System** is a production-ready backend application designed to manage employees, departments, authentication, and authorization in a secure and scalable environment.

The project is built using **Java 21**, **Spring Boot**, **Spring Security**, **JWT Authentication**, **MySQL**, **Redis**, and **Docker**, following enterprise-level backend development practices.

It implements a clean layered architecture with RESTful APIs, Role-Based Access Control (RBAC), Redis caching, DTO mapping, global exception handling, request validation, and secure authentication using Access & Refresh Tokens.

This project demonstrates how modern Spring Boot applications are structured for real-world enterprise systems with a strong focus on scalability, maintainability, security, and performance.

---

# ✨ Features

## 🔐 Authentication & Security

- JWT Access Token Authentication
- Refresh Token Authentication
- Secure Login & Logout
- Role-Based Access Control (RBAC)
- Password Encryption using BCrypt
- Spring Security Integration

## 👨‍💼 Employee Management

- Employee CRUD Operations
- Employee Profile Management
- Search Employees
- Pagination & Sorting
- DTO Mapping
- Request Validation

## 🏢 Department Management

- Department CRUD Operations
- Employee-Department Relationship Management

## ⚡ Performance

- Redis Caching
- Optimized Database Queries

## 🛠 Backend Engineering

- RESTful APIs
- Layered Architecture
- Global Exception Handling
- Standardized API Responses
- Docker Support

---

# 🛠 Tech Stack

| Category | Technology |
|-----------|------------|
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security, JWT |
| Database | MySQL |
| Cache | Redis |
| ORM | Spring Data JPA, Hibernate |
| Build Tool | Maven |
| Containerization | Docker, Docker Compose |
| Validation | Jakarta Validation |
| API Testing | Postman |
| Version Control | Git & GitHub |

<br>

<p align="center">
<img src="https://skillicons.dev/icons?i=java,spring,mysql,redis,docker,maven,git,github,idea,postman"/>
</p>

---

# 🏗 Project Architecture

The project follows a layered architecture.

```text
                 Client (Frontend/Postman)
                          │
                          ▼
                 Spring Security Filter
                          │
                          ▼
                JWT Authentication Filter
                          │
                          ▼
                     REST Controller
                          │
                          ▼
                         Service
                          │
                          ▼
                      Repository
                     ┌─────────────┐
                     ▼             ▼
                  MySQL       Redis Cache
```

---

## 📌 Request Flow

1. Client sends request

2. Spring Security intercepts request

3. JWT Token validation

4. Controller

5. Service Layer

6. Repository Layer

7. MySQL Database

8. Redis Cache

9. API Response

---

# 📁 Project Structure

```text
Employee-ManagementSystem
│
├── src
│   ├── main
│   │   ├── java
│   │   │
│   │   ├── controller
│   │   ├── service
│   │   ├── repository
│   │   ├── entity
│   │   ├── dto
│   │   ├── mapper
│   │   ├── security
│   │   ├── config
│   │   ├── exception
│   │   ├── util
│   │   └── EmployeeManagementApplication.java
│   │
│   └── resources
│       ├── application.properties
│       └── ...
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

# 🚀 Getting Started

Follow the steps below to set up the project locally.

## 📋 Prerequisites

Before running the application, ensure you have the following installed:

- Java 21
- Maven 3.9+
- MySQL
- Redis
- Docker & Docker Compose (Optional)
- Git
- IntelliJ IDEA / VS Code

---

## 📥 Clone the Repository

```bash
git clone https://github.com/JavaDeveloper-Sinku/Employee-ManagementSystem.git

cd Employee-ManagementSystem
```

---

## 📦 Install Dependencies

```bash
mvn clean install
```

---

## ▶️ Run the Application

```bash
mvn spring-boot:run
```

Application will start at:

```text
http://localhost:8080
```

---

# ⚙️ Environment Variables

Configure the following properties inside:

```text
src/main/resources/application.properties
```

## MySQL

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_management

spring.datasource.username=YOUR_USERNAME

spring.datasource.password=YOUR_PASSWORD
```

## Redis

```properties
spring.data.redis.host=localhost

spring.data.redis.port=6379
```

## JWT

```properties
jwt.secret=YOUR_SECRET_KEY

jwt.access-token.expiration=900000

jwt.refresh-token.expiration=604800000
```

## Server

```properties
server.port=8080
```

---

# 🔐 Authentication Flow

The application uses **Spring Security** with **JWT Authentication** and **Refresh Tokens**.

```text
               User Login
                    │
                    ▼
         Email & Password
                    │
                    ▼
 Spring Security Authentication
                    │
                    ▼
Generate Access Token + Refresh Token
                    │
                    ▼
 Store Tokens on Client
                    │
                    ▼
 Protected REST APIs
                    │
                    ▼
 JWT Filter Validation
                    │
                    ▼
 Controller → Service → Repository
                    │
                    ▼
           MySQL / Redis
                    │
                    ▼
             API Response
```

### Authentication Features

- JWT Authentication
- Refresh Tokens
- Secure Login
- Secure Logout
- BCrypt Password Encryption
- Stateless Authentication
- Role-Based Access Control (RBAC)

---

# 📡 API Endpoints

## 🔐 Authentication APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register User |
| POST | `/api/auth/login` | User Login |
| POST | `/api/auth/refresh-token` | Refresh Access Token |
| POST | `/api/auth/logout` | Logout User |

---

## 👨‍💼 Employee APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | Get All Employees |
| GET | `/api/employees/{id}` | Get Employee By ID |
| POST | `/api/employees` | Create Employee |
| PUT | `/api/employees/{id}` | Update Employee |
| DELETE | `/api/employees/{id}` | Delete Employee |

---

## 🏢 Department APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/departments` | Get All Departments |
| GET | `/api/departments/{id}` | Get Department |
| POST | `/api/departments` | Create Department |
| PUT | `/api/departments/{id}` | Update Department |
| DELETE | `/api/departments/{id}` | Delete Department |

---

## 📖 Swagger Documentation

After running the application:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# ⚡ Redis Cache

Redis is used to improve application performance by caching frequently accessed data.

## Cache Flow

```text
Client Request
      │
      ▼
Check Redis Cache
      │
 ┌────┴────┐
 │         │
 ▼         ▼
Hit       Miss
 │          │
 ▼          ▼
Return    MySQL
Cached     Data
Data        │
            ▼
      Store in Redis
            │
            ▼
      Return Response
```

### Spring Cache

- `@Cacheable`
- `@CachePut`
- `@CacheEvict`

---

# 🐳 Docker Setup

Run the complete application using Docker Compose.

## Start

```bash
docker compose up --build
```

## Stop

```bash
docker compose down
```

## Rebuild

```bash
docker compose up --build --force-recreate
```

## Running Containers

```bash
docker ps
```

---

## Docker Architecture

```text
                Docker Compose
                       │
        ┌──────────────┼──────────────┐
        │              │              │
        ▼              ▼              ▼
 Spring Boot       MySQL          Redis
 Application      Database         Cache
```

---

# 📸 Screenshots

## 🔐 Login API

```
screenshots/login-api.png
```

---

## 👨‍💼 Employee CRUD

```
screenshots/employee-crud.png
```

---

## 📖 Swagger UI

```
screenshots/swagger-ui.png
```

---

## ⚡ Redis Cache

```
screenshots/redis-cache.png
```

---

## 🐳 Docker

```
screenshots/docker-containers.png
```

---

## 🗄️ MySQL Database

```
screenshots/mysql-database.png
```

---

# 🚀 Future Improvements

- Email Notifications
- Audit Logs
- File Upload Support
- Elasticsearch Integration
- CI/CD Pipeline
- Kubernetes Deployment
- Monitoring with Prometheus & Grafana

---

# 👨‍💻 Author

**Sinku Singh**

Java Backend Developer

- 💼 GitHub: https://github.com/JavaDeveloper-Sinku
- 🌐 Portfolio: https://sinku-portfolio.vercel.app
- 💼 LinkedIn: https://linkedin.com/in/sinku-singh
- 📧 Email: singh173@gmail.com

---

# 🤝 Contributing

Contributions are welcome!

1. Fork the repository.
2. Create your feature branch.

```bash
git checkout -b feature/new-feature
```

3. Commit your changes.

```bash
git commit -m "Add new feature"
```

4. Push your branch.

```bash
git push origin feature/new-feature
```

5. Open a Pull Request.

---

# 📄 License

This project is licensed under the **MIT License**.

---

<div align="center">

⭐ If you found this project helpful, please consider giving it a **Star** on GitHub!

Made with ❤️ by **Sinku Singh**

</div>
