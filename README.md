# Smart Campus Management System

A console-based Java 17 and MySQL application using JDBC. It includes student, faculty, attendance, complaint and event management, with a username/password login.

## Modules

- **Login:** authenticates an application user using a BCrypt password hash.
- **Student management:** add, list and delete students.
- **Faculty management:** add faculty records.
- **Attendance:** record a student's Present/Absent attendance for a subject.
- **Complaints:** record a complaint with Pending status.
- **Events:** add an event with a date and location.

## Requirements

- JDK 17 or later
- Maven 3.9 or later
- MySQL 8 or a compatible MySQL server

## Setup

### 1. Create the database

Run the SQL script from the repository root:

```bash
mysql -u root -p < schema.sql
```

This creates the `smart_campus` database and all tables required by the Java modules. It does not create an application login with a hard-coded password.

### 2. Configure database and first admin

Copy the example configuration:

**Windows PowerShell**
```powershell
Copy-Item .env.example .env
```

**macOS / Linux**
```bash
cp .env.example .env
```

Edit `.env` with your local MySQL account. Before the first login, set both `DB_ADMIN_USERNAME` and `DB_ADMIN_PASSWORD`. Choose a private password with at least 12 characters. On the first application login, the program creates that account in the `users` table using a BCrypt hash. It does not use a built-in default credential.

The app reads settings in this order: operating-system environment variables, Java system properties, then the local `.env` file. Never commit `.env`.

### 3. Build and run

```bash
mvn clean compile
mvn exec:java
```

The application prompts for the admin username and password, then displays the Smart Campus Management System menu. Use option 8 to exit.

## Configuration

| Variable | Purpose | Default |
|---|---|---|
| `DB_URL` | JDBC connection URL | `jdbc:mysql://localhost:3306/smart_campus` |
| `DB_USER` | MySQL username | `root` |
| `DB_PASSWORD` | MySQL password | empty |
| `DB_ADMIN_USERNAME` | First account to create when missing | not configured |
| `DB_ADMIN_PASSWORD` | Password for the first account; BCrypt-hashed before storage | not configured |

## Repository layout

Java source files are kept at the repository root in the default package for compatibility with the original project. Maven compiles those root-level files into `target/classes`.

- `AppConfig.java` — environment and local `.env` configuration
- `DBConnection.java` — JDBC connections
- `ConsoleInput.java` — shared console scanner
- `Login.java` — BCrypt login and first-admin bootstrap
- `*Management.java` — feature modules
- `schema.sql` — MySQL schema
- `pom.xml` — Maven build and dependencies

## Security notes and limitations

- Passwords are stored as BCrypt hashes. No sample or default admin password is hard-coded.
- SQL statements use prepared statements for user input.
- Connections, statements and result sets are closed with try-with-resources.
- The project is an educational console application. It does not yet implement role-based authorization, account recovery, audit history or an administrative UI. Configure a dedicated least-privilege MySQL user before using it outside a local learning environment.

No automated integration tests are currently included; verify it against a local MySQL database after setup.
