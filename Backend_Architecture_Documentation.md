# Employee Management System - Backend Architecture Documentation

## Overview
The backend of the Employee Management System is a REST API built using the Spring Boot framework. It provides all the necessary endpoints for managing employee data, departments, locations, and tiers, as well as complex queries and reporting capabilities. The system is designed with security in mind, implementing JWT-based authentication and authorization.

## Technologies Used

- **Framework**: Spring Boot 3.3.5
- **Programming Language**: Java 17
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT authentication
- **Data Processing**: Apache POI (Excel), OpenCSV (CSV)
- **Web Token**: JJWT for JSON Web Token handling
- **Build Tool**: Maven

## Architecture Layers

### 1. Controller Layer
The controller layer is responsible for handling HTTP requests and responses. Each controller corresponds to a specific entity or functionality:
- **DepartmentController**: Manages department-related operations
- **EmployeeController**: Manages employee-related operations
- **LocationController**: Manages location-related operations
- **TierController**: Manages tier-related operations
- **AuthController**: Handles authentication endpoints
- **ComplexQueryController**: Provides endpoints for complex queries
- **ApiCallHistoryController**: Manages API call history data

### 2. Service Layer
The service layer implements business logic and acts as an intermediary between the controller and data access layers. Services provide methods for:
- Business validation
- Transaction management
- Complex operations involving multiple entities
- Data processing operations

### 3. Data Access Layer (DAO)
The DAO layer handles database interactions using Spring's JDBC template. It includes:
- Direct SQL query execution
- Database connection management
- Result set mapping

### 4. Model Layer
Models represent the data structures that map directly to database tables. Each model corresponds to a specific table in the database schema.

### 5. DTO (Data Transfer Object) Layer
DTOs are used for transferring data between layers and over the network. Common types include:
- Request DTOs for input validation
- Response DTOs for API responses
- Form DTOs for handling form submissions

## Security Architecture

### Authentication
- JWT (JSON Web Token) based authentication
- Login and registration endpoints
- Password encryption using Spring Security's password encoder
- Token generation and validation

### Authorization
- Role-based access control
- Secure endpoints that require valid JWT tokens
- Automatic token validation through interceptors

## Database Integration

### Configuration
- PostgreSQL database connection via application.properties
- Connection pooling and configuration
- SQL logging enabled for debugging

### Data Processing
- Excel file generation using Apache POI
- CSV file generation using OpenCSV
- Export functionality for various data types

## API Design

### REST Endpoints
- Standard RESTful API design patterns
- CRUD operations for all entities
- Proper HTTP status codes
- Consistent response format

### Error Handling
- Global exception handling
- Proper error responses
- Validation error handling

## Configuration and Utilities

### Application Configuration
- Database connection settings
- JWT configuration
- Server configuration
- Logging setup

### Utility Components
- Token utilities for JWT handling
- Date/time utilities
- File processing utilities
- Data export utilities

## Event Handling
The system includes an event layer that handles specific business events, such as logging API calls, which contributes to the API call history functionality.

## Interceptor Layer
Custom interceptors are used for:
- Request logging
- Performance monitoring
- Security checks

## Testing
The application includes comprehensive testing with:
- Unit tests for services and controllers
- Integration tests
- Security tests
    