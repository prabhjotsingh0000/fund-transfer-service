# Fund Transfer Service

This is a Spring Boot REST API for a simple fund transfer service. It allows users to transfer funds between accounts and retrieve transfer details.

## Features
- Create a new fund transfer
- Retrieve a transfer by ID
- Retrieve all transfers for a specific user
- Cancel a transfer if it is still pending
- In-memory H2 database for persistence

## Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database (in-memory)
- MapStruct (for DTO mapping)
- JUnit & Mockito (for testing)

## API Endpoints

### 1. Create a Transfer
**Endpoint:** `POST /api/transfer`
- **Request Body:**
  ```json
  {
    "fromAccount": "123456789",
    "toAccount": "987654321",
    "amount": 100.50,
    "description": "Payment for invoice #123"
  }
  ```
- **Response:**
  ```json
  {
    "id": 1,
    "fromAccount": "123456789",
    "toAccount": "987654321",
    "amount": 100.50,
    "description": "Payment for invoice #123",
    "status": "PENDING",
    "createdAt": "2025-04-02T12:00:00"
  }
  ```

### 2. Get Transfer by ID
**Endpoint:** `GET /api/transfer/{id}`
- **Response:**
  ```json
  {
    "id": 1,
    "fromAccount": "123456789",
    "toAccount": "987654321",
    "amount": 100.50,
    "description": "Payment for invoice #123",
    "status": "PENDING",
    "createdAt": "2025-04-02T12:00:00"
  }
  ```

### 3. Get Transfers for a User
**Endpoint:** `GET /api/transfer/user/{accountNumber}?page=0&size=10`
- **Response:**
  ```json
  {
    "content": [
      {
        "id": 1,
        "amount": 100.50,
        "status": "PENDING",
        "createdAt": "2025-04-02T12:00:00"
      }
    ],
    "totalElements": 1,
    "totalPages": 1,
    "size": 10,
    "number": 0
  }
  ```

### 4. Cancel a Transfer
**Endpoint:** `PUT /api/transfer/{id}/cancel`
- **Response:**
  ```json
  {
    "id": 1,
    "fromAccount": "123456789",
    "toAccount": "987654321",
    "amount": 100.50,
    "description": "Payment for invoice #123",
    "status": "CANCELLED",
    "createdAt": "2025-04-02T12:00:00"
  }
  ```

### 5. Complete a Transfer
**Endpoint:** `PUT /api/transfer/{id}/complete`
- **Response:**
  ```json
  {
    "id": 1,
    "fromAccount": "123456789",
    "toAccount": "987654321",
    "amount": 100.50,
    "description": "Payment for invoice #123",
    "status": "COMPLETED",
    "createdAt": "2025-04-02T12:00:00"
  }
  ```
  
## Running the Application

### Prerequisites
- Java 17+
- Gradle

### Steps to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/prabhjotsingh0000/fund-transfer-service.git
   cd fund-transfer-service
   ```
2. Build the application:
   ```sh
   ./gradlew build
   ```
3. Run the application:
   ```sh
   ./gradlew bootRun
   ```
4. The API will be available at `http://localhost:8080/api/transfer`

### H2 Database Console
- You can access the H2 console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:fundtransferdb`
- Username: `sa`
- Password: *(leave blank)*

## Running Tests
To run unit tests, execute:
```sh
./gradlew test
```

## Future Enhancements
- Add authentication & authorization
- Introduce database persistence (PostgreSQL or MySQL)
- Improve error handling with custom exceptions



