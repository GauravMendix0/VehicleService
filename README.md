````markdown
#  Vehicle Service Application

A Spring Boot REST API for managing **Vehicle Owners**, **Vehicles**, and **Service Appointments**.

It includes endpoints for CRUD operations, stores data in an in-memory H2 database, and has JUnit tests for controller logic.

---

##  Features

- Manage entities: Owner, Vehicle, and ServiceAppointment
- RESTful API architecture
- In-memory H2 Database with console access
- Unit tests using MockMvc & JUnit 5
- Auto-creation of tables using Spring Data JPA

---

##  Tech Stack

| Technology         | Purpose                      |
|--------------------|-------------------------------|
| Java (17+)         | Core language                |
| Spring Boot 3.5.0  | Framework                    |
| Spring Web         | RESTful APIs                 |
| Spring Data JPA    | ORM for DB interaction       |
| H2 Database        | In-memory testing database   |
| JUnit 5 + MockMvc  | Unit testing                 |

---

##  Tables in H2 Database

On application startup, the following tables are auto-created:

| Table Name             | Fields                                     |
|------------------------|--------------------------------------------|
| `owner`                | `oid`, `name`, `contact`                   |
| `vehicle`              | `vid`, `model`, `type`, `owner_id (FK)`    |
| `service_appointment`  | `aid`, `description`, `vehicle_id (FK)`    |

>  Relationships:
- One `Owner` → Many `Vehicles`
- One `Vehicle` → Many `ServiceAppointments`

---

##  Sample Data to Insert

If using H2 Console (`http://localhost:8080/h2-console`), run the following queries to test the API:

```sql
-- Insert sample owner
INSERT INTO OWNER (oid, name, contact) VALUES (1, 'Gaurav', '1234567890');

-- Insert vehicle for owner
INSERT INTO VEHICLE (vid, model, type, owner_id) VALUES (1, 'Honda City', 'Sedan', 1);

-- Insert service appointment for vehicle
INSERT INTO SERVICE_APPOINTMENT (aid, description, vehicle_id) VALUES (1, 'Oil change', 1);
````

---

##  Running the Project

###  Clone and Navigate

```bash
git clone https://github.com/your-username/vehicle-service.git
cd vehicle-service
```

###  Run the App

```bash
./mvnw spring-boot:run
```

Or directly run `VehicleServiceApplication.java` in IntelliJ.

 App starts on: `http://localhost:8080`

---

##  API Endpoints

###  Owner APIs

| Method | Endpoint       | Description            |
| ------ | -------------- | ---------------------- |
| GET    | `/owners`      | List all owners        |
| GET    | `/owners/{id}` | Get owner by ID        |
| POST   | `/owners`      | Create new owner       |
| PUT    | `/owners/{id}` | Update owner fully     |
| PATCH  | `/owners/{id}` | Update owner partially |
| DELETE | `/owners/{id}` | Delete owner by ID     |

>  Similar APIs can be implemented for `/vehicles` and `/appointments`.

---

##  Running Unit Tests

```bash
./mvnw test
```

Tests cover:

* All basic CRUD API flows for `Owner`
* Using `@WebMvcTest`, `@MockBean`, `MockMvc`

---

##  H2 Database Configuration

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

* H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

    * JDBC URL: `jdbc:h2:mem:testdb`
    * Username: `sa`, Password: *(leave empty)*

---

##  Folder Structure

```
src/
├── controller/
│   └── ControllerOwner.java
├── entity/
│   ├── Owner.java
│   ├── Vehicle.java
│   └── ServiceAppointment.java
├── repo/
│   ├── RepoOwner.java
│   └── ...
├── VehicleServiceApplication.java
└── test/
    └── ControllerOwnerTest.java
```

---