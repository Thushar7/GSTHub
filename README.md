# GSTHub

<p>
  <strong>A Spring Boot foundation for GST compliance operations, client management, reconciliation, billing, and workflow tracking.</strong>
</p>

<p>
  <a href="https://github.com/Thushar7/GSTHub"><img alt="GitHub Repository" src="https://img.shields.io/badge/GitHub-GSTHub-181717?style=for-the-badge&logo=github"></a>
  <img alt="Java" src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white">
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-4.2.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img alt="MySQL" src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img alt="Maven" src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white">
</p>

> **Repository status:** GSTHub currently contains a Java/Spring Boot backend foundation with JPA entities, repositories, MySQL configuration, and Flyway database migrations. Controller and frontend layers are not yet present in this repository.

---

## Overview

GSTHub is designed as a centralized platform for GST-focused service teams. The application models the operational relationships between clients, GST registrations, compliance obligations, assigned staff, documents, reconciliations, billing, payments, notifications, and audit records.

The project uses a layered Spring architecture and exposes a relational model prepared for tax practice management workflows.

## Key Capabilities

- **Client management** — business profiles, PAN, GST registrations, contacts, and service assignments
- **Compliance tracking** — GST obligations, filing periods, due dates, review statuses, and acknowledgements
- **Task operations** — internal work allocation with priorities and completion tracking
- **Document workflow** — document uploads, verification, and document requests
- **Reconciliation** — purchase/GSTR-2B import batches and line-level reconciliation runs
- **Billing** — invoices, invoice line items, payments, and payment allocations
- **Follow-ups** — scheduled client communication and follow-up outcomes
- **Notifications** — user-level operational alerts
- **Audit trail** — trackable actions, entities, and request metadata
- **Role-based structure** — application roles and assigned staff represented in the data model

## Technology Stack

| Category | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.2.0 |
| Persistence | Spring Data JPA / Hibernate |
| Database | MySQL |
| Migrations | Flyway |
| Security dependency | Spring Security |
| Validation | Spring Validation |
| Web | Spring Web MVC |
| Monitoring | Spring Boot Actuator |
| Build tool | Maven Wrapper |
| Tests | Spring Boot Test, H2, Flyway Test support |

## Data Model

The schema is organized around the complete GST service lifecycle:

```text
Clients
├── Registrations
├── Contacts
├── Services
├── Assignments
└── Compliance Obligations
    ├── Tasks
    ├── Documents
    ├── Document Requests
    ├── Follow-ups
    └── Reconciliation Runs
        └── Reconciliation Items

Billing
├── Invoices
│   └── Invoice Items
└── Payments
    └── Payment Allocations

Users and Operations
├── Roles
├── Notifications
├── Import Batches
└── Audit Logs
```

The initial migration creates tables for clients, users, roles, registrations, service types, compliance obligations, tasks, documents, imports, reconciliation, invoices, payments, notifications, and audit logs.

## Project Structure

```text
GSTHub/
├── .mvn/wrapper/
├── src/
│   ├── main/
│   │   ├── java/com/example/GSTHub/
│   │   │   ├── Model/        # JPA domain entities
│   │   │   ├── Repository/   # Spring Data repositories
│   │   │   └── GstHubApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/
│   │           └── V1__create_gstflow_tables.sql
│   └── test/java/com/example/GSTHub/
│       ├── GstHubApplicationTests.java
│       └── repository/RepositoryLayerTest.java
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## Prerequisites

Before running the project, make sure you have:

- **JDK 17** or newer
- **MySQL 8+**
- **Git**
- Internet access for Maven dependencies

## Database Setup

Create a MySQL database named `gstflow_db`:

```sql
CREATE DATABASE gstflow_db;
```

The default development configuration is:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gstflow_db
spring.datasource.username=root
spring.datasource.password=root@123
```

### Recommended configuration

Avoid storing real credentials directly in source control. After setup, override the database values using environment variables or Maven profiles.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gstflow_db?useSSL=false&serverTimezone=Asia/Kolkata&allowPublicKeyRetrieval=true
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

## Running Locally

### 1. Clone the repository

```bash
git clone https://github.com/Thushar7/GSTHub.git
cd GSTHub
```

### 2. Configure MySQL

Update `src/main/resources/application.properties` or provide equivalent environment-based configuration.

### 3. Run the application

#### Linux / macOS

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

#### Windows

```bat
mvnw.cmd spring-boot:run
```

The application is configured to start on:

```text
http://localhost:8080
```

## Build the Application

```bash
./mvnw clean package
```

Windows:

```bat
mvnw.cmd clean package
```

The generated JAR will be available in:

```text
target/GSTHub-0.0.1-SNAPSHOT.jar
```

Run it directly:

```bash
java -jar target/GSTHub-0.0.1-SNAPSHOT.jar
```

## Running Tests

```bash
./mvnw test
```

The test configuration uses repository and Spring context tests, with H2 available as a test dependency.

## Configuration

| Property | Description | Default |
|---|---|---|
| `spring.application.name` | Application name | `GSTHub` |
| `server.port` | HTTP server port | `8080` |
| `spring.datasource.url` | MySQL JDBC URL | `jdbc:mysql://localhost:3306/gstflow_db` |
| `spring.datasource.username` | Database username | `root` |
| `spring.datasource.password` | Database password | Configured locally |
| `spring.jpa.hibernate.ddl-auto` | Hibernate schema strategy | `validate` |
| `spring.flyway.enabled` | Enables database migrations | `true` |

## Development Roadmap

Potential next steps for this repository:

- [ ] Add REST controllers and API DTOs
- [ ] Implement authentication and role-based authorization
- [ ] Add service-layer validation and business rules
- [ ] Build GST filing and deadline dashboards
- [ ] Add file storage and document upload APIs
- [ ] Add GSTR-2B purchase reconciliation processing
- [ ] Create the billing and payment workflows
- [ ] Add notifications and scheduled reminders
- [ ] Configure profiles for development and production
- [ ] Expand integration and API test coverage
- [ ] Add API documentation with Swagger/OpenAPI
- [ ] Build a web or mobile frontend

## Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch

   ```bash
   git checkout -b feature/your-feature
   ```

3. Commit your changes

   ```bash
   git commit -m "Add your feature"
   ```

4. Push the branch

   ```bash
   git push origin feature/your-feature
   ```

5. Open a pull request

## License

No license has been added to this repository yet. Add a license file before using the project for commercial or public distribution.

## Author

**Thushar7**

- GitHub: [@Thushar7](https://github.com/Thushar7)
- Repository: [GSTHub](https://github.com/Thushar7/GSTHub)
