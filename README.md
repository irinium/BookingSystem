## Booking System API

The Booking System API is a Spring Boot application for managing units, bookings, and payments. It uses an OpenAPI specification to automatically generate API models and interfaces, ensuring that the implementation stays in sync with its documentation. The application uses PostgreSQL (with Liquibase for schema management) and Redis (for caching available unit counts). It is built with Gradle and packaged for distribution.

### Features

- Unit Management: Create units with cost, accommodation type, and availability.
- Booking Management: Book a unit for a date range, cancel bookings, and verify overlapping bookings.
- Payment Emulation: Update payment status for bookings.
- Statistics: Retrieve the count of available units, cached with Redis.
- Caching: Uses Spring Cache with Redis for performance.
- Database: PostgreSQL managed by Liquibase.
- API Documentation: Swagger/OpenAPI provided.

### Prerequisites

Java: JDK 21 or later.
Docker & Docker Compose: To run PostgreSQL and Redis containers.
Gradle: v8 or later.

### Running Locally from a ZIP Package

Follow these steps to run the service locally when you have the project as a ZIP file:

1. Unzip the Project
   Extract the contents of the ZIP file to a local directory.

2. Start the Required Docker Containers
   Ensure Docker and Docker Compose are installed and running on your system.
3. Open a terminal in the project directory.

   `cd scripts/docker
    docker-compose up -d`

This will start:

- PostgreSQL: A PostgreSQL container for the database.
- Redis: A Redis container for caching.

3. Build and Run the Application

`gradle clean build`

`gradle bootRun`

The application will start on port 8080 by default.

4. Access API Documentation:
Visit http://localhost:8080/swagger-ui/index.html to view the Swagger UI.

5. Access the API

   API Endpoints:
    - Create Unit: POST /api/unit
    - List Units: GET /api/unit
    - Book Unit: POST /api/unit/{unitId}/book
    - Cancel Booking: POST /api/unit/{unitId}/cancel
    - Emulate Payment: POST /api/unit/{unitId}/pay
    - Available Units Count: GET /stats/available-units
    - Swagger UI: View API documentation by navigating to:
      http://localhost:8080/swagger-ui/index.html

6. Running Tests
   To execute the unit and integration tests, run:

   `gradle test`

Test reports will be generated under build/reports/tests/test/index.html.

7. Database Connection:
   Confirm that the PostgreSQL container is up and that the credentials in your application.yml.

8. Redis Connection:
   Verify that the Redis container is running and accessible at the configured host and port.


