# 👨‍💼 Employee Management System

A production-ready **Employee Management System** built using **Java 21, Spring Boot, Spring Security, JWT Authentication, MySQL, Redis, Docker, and REST APIs**.

This project demonstrates enterprise backend development practices, including secure authentication and authorization, role-based access control (RBAC), Redis caching, DTO mapping, global exception handling, pagination, and containerized deployment. It follows a clean layered architecture to build scalable and maintainable RESTful services.
---

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring_Security-6.x-6DB33F?style=for-the-badge&logo=springsecurity)
![JWT](https://img.shields.io/badge/JWT-Authentication-black?style=for-the-badge&logo=jsonwebtokens)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=for-the-badge&logo=mysql)
![Redis](https://img.shields.io/badge/Redis-Cache-DC382D?style=for-the-badge&logo=redis)
![Docker](https://img.shields.io/badge/Docker-Container-2496ED?style=for-the-badge&logo=docker)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)


## ✨ Features

### 🔐 Authentication & Security
- JWT Access Token Authentication
- Refresh Token Authentication
- Secure Login & Logout
- Role-Based Access Control (RBAC)
- Password Encryption using BCrypt
- Spring Security Integration

### 👨‍💼 Employee Management
- Create, Read, Update, and Delete (CRUD) Employees
- Search Employees
- Pagination & Sorting
- Input Validation
- DTO-Based Request & Response Handling

### 🏢 Department Management
- Department CRUD Operations
- Employee-Department Relationship Management

### ⚡ Performance
- Redis Caching for Frequently Accessed Data
- Optimized Database Queries

### 🛠️ Backend Engineering
- RESTful API Design
- Layered Architecture (Controller → Service → Repository)
- Global Exception Handling
- Centralized API Response Structure
- MySQL Database Integration
- Docker Support

## 🛠️ Tech Stack

| Category | Technologies |
|----------|--------------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.x |
| **Security** | Spring Security, JWT Authentication |
| **Database** | MySQL |
| **Caching** | Redis |
| **ORM** | Spring Data JPA, Hibernate |
| **Build Tool** | Maven |
| **Containerization** | Docker, Docker Compose |
| **Utilities** | Lombok, Validation API |
| **API Testing** | Postman |
| **Version Control** | Git, GitHub |


### 💻 Technologies Used

<p align="left">
  <img src="https://skillicons.dev/icons?i=java,spring,mysql,redis,docker,git,github,maven,idea,postman" />
</p>



## 📁 Project Structure

```
Employee-ManagementSystem/
│
├── src/main/java
│   └── com/example/ems
│       ├── controller/
│       │   ├── EmployeeController.java        # Employee API endpoints
│       │   ├── DepartmentController.java      # Department API endpoints
│       │   └── ReportController.java          # Report generation endpoints
│       │
│       ├── service/
│       │   ├── EmployeeService.java           # Employee business logic
│       │   ├── DepartmentService.java         # Department business logic
│       │   ├── SalaryService.java             # Salary calculations
│       │   └── ReportService.java             # Report generation logic
│       │
│       ├── repository/
│       │   ├── EmployeeRepository.java        # Employee data access
│       │   └── DepartmentRepository.java      # Department data access
│       │
│       ├── entity/
│       │   ├── Employee.java                  # Employee entity
│       │   ├── Department.java                # Department entity
│       │   └── Salary.java                    # Salary entity
│       │
│       ├── dto/
│       │   ├── EmployeeDTO.java               # Employee data transfer object
│       │   ├── DepartmentDTO.java             # Department DTO
│       │   └── ResponseDTO.java               # Generic response wrapper
│       │
│       ├── exception/
│       │   ├── EmployeeNotFoundException.java  # Custom exception
│       │   └── GlobalExceptionHandler.java    # Global exception handling
│       │
│       ├── util/
│       │   └── ValidationUtil.java            # Validation utilities
│       │
│       └── EmployeeManagementApplication.java # Main application class
│
├── src/main/resources
│   ├── application.properties                 # Application configuration
│   ├── data.sql                              # Sample database data
│   └── schema.sql                            # Database schema (optional)
│
├── src/test/java
│   └── com/example/ems
│       ├── controller/
│       │   └── EmployeeControllerTest.java
│       ├── service/
│       │   └── EmployeeServiceTest.java
│       └── repository/
│           └── EmployeeRepositoryTest.java
│
├── pom.xml                                    # Maven configuration
└── README.md                                  # This file
```

---

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK)** 17 or higher
- **Apache Maven** 3.6 or higher
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)
- **Git** for version control

### Installation

1. **Clone the Repository**

```bash
git clone https://github.com/JavaDeveloper-Sinku/Employee-ManagementSystem.git
cd Employee-ManagementSystem
```

2. **Install Dependencies**

```bash
mvn clean install
```

This command downloads all required dependencies from Maven Central Repository.

3. **Run the Application**

#### Option 1: Using Maven

```bash
mvn spring-boot:run
```

#### Option 2: Using IDE

- Import project into IntelliJ IDEA or Eclipse
- Right-click on `EmployeeManagementApplication.java`
- Select **Run**

#### Option 3: Build and Execute JAR

```bash
mvn clean package
java -jar target/Employee-Management-0.0.1-SNAPSHOT.jar
```

4. **Access the Application**

- **Application URL:** http://localhost:8080
- **H2 Console:** http://localhost:8080/h2-console

### H2 Database Access

When you access the H2 console:

```
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (leave blank)
```

---

## 📡 API Endpoints

### Employee Endpoints

| Method | Endpoint | Description | Status |
|--------|----------|-------------|--------|
| GET | `/api/employees` | Get all employees | 200 |
| GET | `/api/employees/{id}` | Get employee by ID | 200 |
| POST | `/api/employees` | Create new employee | 201 |
| PUT | `/api/employees/{id}` | Update employee | 200 |
| DELETE | `/api/employees/{id}` | Delete employee | 204 |
| GET | `/api/employees/search` | Search employees | 200 |

### Department Endpoints

| Method | Endpoint | Description | Status |
|--------|----------|-------------|--------|
| GET | `/api/departments` | Get all departments | 200 |
| GET | `/api/departments/{id}` | Get department by ID | 200 |
| POST | `/api/departments` | Create new department | 201 |
| PUT | `/api/departments/{id}` | Update department | 200 |
| DELETE | `/api/departments/{id}` | Delete department | 204 |

### Report Endpoints

| Method | Endpoint | Description | Status |
|--------|----------|-------------|--------|
| GET | `/api/reports/employee/{id}` | Generate employee background report | 200 |
| GET | `/api/reports/department/{id}` | Generate department report | 200 |
| POST | `/api/reports/export` | Export reports to PDF/Excel | 200 |

---

## 📋 API Request & Response Examples

### Create Employee

**Request:**
```http
POST /api/employees
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "9876543210",
  "departmentId": 1,
  "designation": "Software Engineer",
  "salary": 50000,
  "joinDate": "2024-01-15",
  "status": "ACTIVE"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "9876543210",
  "department": {
    "id": 1,
    "name": "IT"
  },
  "designation": "Software Engineer",
  "salary": 50000,
  "joinDate": "2024-01-15",
  "status": "ACTIVE",
  "createdAt": "2024-01-10T10:30:00",
  "updatedAt": "2024-01-10T10:30:00"
}
```

### Get All Employees

**Request:**
```http
GET /api/employees
```

**Response (200 OK):**
```json
{
  "status": "success",
  "message": "Employees retrieved successfully",
  "data": [
    {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "designation": "Software Engineer",
      "department": "IT",
      "salary": 50000
    }
  ],
  "timestamp": "2024-01-10T10:30:00"
}
```

### Generate Employee Background Report

**Request:**
```http
GET /api/reports/employee/1
```

**Response (200 OK):**
```json
{
  "employeeId": 1,
  "employeeName": "John Doe",
  "designation": "Software Engineer",
  "department": "IT",
  "joinDate": "2024-01-15",
  "totalExperience": "3 years",
  "salary": 50000,
  "performanceRating": 4.5,
  "attendance": "95%",
  "projectsCompleted": 12,
  "generatedDate": "2024-01-10T10:30:00"
}
```

---

## 🗄️ Database Schema

### Employee Table

```sql
CREATE TABLE employee (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  phone VARCHAR(20),
  department_id BIGINT,
  designation VARCHAR(100),
  salary DECIMAL(10, 2),
  join_date DATE,
  status VARCHAR(20) DEFAULT 'ACTIVE',
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (department_id) REFERENCES department(id)
);
```

### Department Table

```sql
CREATE TABLE department (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  description VARCHAR(255),
  manager_id BIGINT,
  budget DECIMAL(15, 2),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);
```

### Salary Table

```sql
CREATE TABLE salary (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  employee_id BIGINT NOT NULL,
  basic_salary DECIMAL(10, 2),
  allowances DECIMAL(10, 2),
  deductions DECIMAL(10, 2),
  net_salary DECIMAL(10, 2),
  salary_month INT,
  salary_year INT,
  created_at TIMESTAMP,
  FOREIGN KEY (employee_id) REFERENCES employee(id)
);
```

---

## 🏗️ Architecture

### Layered Architecture

```
┌─────────────────────────────────────┐
│         REST Controllers            │
│    (Handle HTTP Requests/Response)  │
└────────────────┬────────────────────┘
                 │
┌─────────────────┴────────────────────┐
│         Service Layer               │
│    (Business Logic & Validation)    │
└────────────────┬────────────────────┘
                 │
┌─────────────────┴────────────────────┐
│      Repository Layer (JPA)         │
│    (Database Operations)            │
└────────────────┬────────────────────┘
                 │
┌─────────────────┴────────────────────┐
│       H2 Database                   │
│    (Data Persistence)               │
└─────────────────────────────────────┘
```

---

## 🧪 Testing

### Run All Tests

```bash
mvn test
```

### Run Specific Test Class

```bash
mvn test -Dtest=EmployeeServiceTest
```

### Run Tests with Coverage

```bash
mvn clean test jacoco:report
```

### Example Unit Test

```java
@SpringBootTest
public class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        
        Employee result = employeeService.getEmployeeById(1L);
        
        assertEquals("John", result.getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
    }
}
```

---

## ⚙️ Configuration

### Application Properties

**src/main/resources/application.properties:**

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.root=INFO
logging.level.com.example.ems=DEBUG
```

### MySQL Configuration (Optional)

To use MySQL instead of H2:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

---

## 🐛 Common Issues & Troubleshooting

| Issue | Solution |
|-------|----------|
| **Port 8080 already in use** | Change port in `application.properties`: `server.port=8081` |
| **H2 Console not accessible** | Enable it in properties: `spring.h2.console.enabled=true` |
| **Entity not mapping** | Check `@Entity` and `@Table` annotations |
| **Dependency injection fails** | Ensure class is annotated with `@Service`, `@Repository`, etc. |
| **Tests failing** | Run `mvn clean test` to clear cache |
| **Lombok not working** | Install Lombok plugin in your IDE |

---

## 📊 Sample Data

The application includes sample data (in `data.sql`):

```sql
INSERT INTO department (name, description) VALUES ('IT', 'Information Technology');
INSERT INTO department (name, description) VALUES ('HR', 'Human Resources');
INSERT INTO department (name, description) VALUES ('Finance', 'Finance Department');

INSERT INTO employee (first_name, last_name, email, phone, department_id, designation, salary, join_date, status)
VALUES ('John', 'Doe', 'john@example.com', '9876543210', 1, 'Software Engineer', 50000, '2024-01-15', 'ACTIVE');

INSERT INTO employee (first_name, last_name, email, phone, department_id, designation, salary, join_date, status)
VALUES ('Jane', 'Smith', 'jane@example.com', '9876543211', 2, 'HR Manager', 45000, '2023-06-20', 'ACTIVE');
```

---

## 🚀 Building for Production

### Create Production Build

```bash
mvn clean package -DskipTests
```

### Deploy on Server

```bash
java -jar target/Employee-Management-0.0.1-SNAPSHOT.jar \
  --server.port=8080 \
  --spring.datasource.url=jdbc:mysql://db-server:3306/employee_db \
  --spring.datasource.username=db_user \
  --spring.datasource.password=db_password
```

### Docker Deployment (Optional)

Create a `Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/Employee-Management-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run:

```bash
docker build -t employee-management-app .
docker run -p 8080:8080 employee-management-app
```

---

## 📚 Learning Resources

- **Spring Boot Documentation** – https://spring.io/projects/spring-boot
- **Spring Data JPA** – https://spring.io/projects/spring-data-jpa
- **Hibernate ORM** – https://hibernate.org/
- **RESTful API Design** – https://restfulapi.net/
- **H2 Database** – https://www.h2database.com/
- **Lombok** – https://projectlombok.org/

---

## 🚀 Future Enhancements

- [ ] JWT Authentication & Authorization
- [ ] Role-based Access Control (Admin, Manager, Employee)
- [ ] Advanced Search with Filters
- [ ] Employee Performance Tracking
- [ ] Attendance Management
- [ ] Leave Management System
- [ ] Payroll Module
- [ ] Export Reports to PDF/Excel
- [ ] Email Notifications
- [ ] Audit Logs
- [ ] API Documentation with Swagger/OpenAPI
- [ ] Caching with Redis
- [ ] Microservices Architecture

---

## 📄 License

This project is open-source and available under the **MIT License**.

---

## 👨‍💻 Author

**Sinku Singh**  
Java Backend Developer | Spring Boot | Enterprise Applications | System Design

- 💼 GitHub: [JavaDeveloper-Sinku](https://github.com/JavaDeveloper-Sinku)
- 📧 Email: singh173@gmail.com
- 💻 Portfolio: [sinku-portfolio.vercel.app](https://sinku-portfolio.vercel.app)
- 🔗 LinkedIn: [Sinku Singh](https://www.linkedin.com/in/sinku-singh-7a22ab233/)

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Workflow

```bash
# Create feature branch
git checkout -b feature/new-feature

# Make changes and commit
git add .
git commit -m "Add new feature"

# Push and create PR
git push origin feature/new-feature
```

---

## ⭐ Support

If you find this project helpful, please consider giving it a **star** ⭐ on GitHub!

---

## 📞 Contact & Support

For issues, suggestions, or questions:
- Open an **Issue** on GitHub
- Email: singh173@gmail.com
- LinkedIn: [Sinku Singh](https://www.linkedin.com/in/sinku-singh-7a22ab233/)

---

## 🔗 Related Projects

- [JWT Authentication System](https://github.com/JavaDeveloper-Sinku/Jwt_AuthenticationSystem)
- [GraphQL CRUD APIs](https://github.com/JavaDeveloper-Sinku/GraphQL-CRUD-APIs)
- [E-Commerce Microservices](https://github.com/JavaDeveloper-Sinku/ecom-Microservice_CodeBase)
- [Finance Dashboard Backend](https://github.com/JavaDeveloper-Sinku/Finance-Data-Processing-and-Access-Control-Backend)

---

**Happy Coding! 🚀👥**
