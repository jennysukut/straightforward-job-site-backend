# Straight Forward Job Site Backend - Spring Boot Application

## Overview

Straight Forward Job Site Backend is a Spring Boot application that provides backend services for a job site.<br/>Key features include
* GraphQL and REST API endpoints
* Persistent data storage and retrieval using a SQL database for data integrity
* Interactions with external services
  * Helcim payment processing
  * Email service

## Table of Contents

- [Architecture](#architecture)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)

## Architecture

Straight Forward Job Site Backend follows a layered architecture:

1. **Controller Layer**: Handles HTTP requests and responses. Implemented using Spring MVC controllers and GraphQL controllers.
2. **Service Layer**: Contains business logic and orchestrates the flow of data between the presentation and data access layers.
3. **Data Access Layer**: Manages data persistence and retrieval. Utilizes Spring Data JPA for database operations.
4. **Domain Layer**: Defines the core domain models and business rules.
5. **Data Transfer Layer**: Defines the schema of request and response data.

### Key Components:

- **Controllers**: Handle incoming HTTP requests and define API endpoints.
- **Services**: Implement business logic and transaction management.
- **Repositories**: Provide data access abstractions.
- **Entities**: Represent domain objects and database tables.
- **DTOs**: Data Transfer Objects for API requests and responses.

## Technologies

- Java 17
- Spring Boot 3.3.x
- Spring Data JPA
- H2 (for now), will switch to more production suitable database when necessary
- Maven
- Swagger for API documentation
- JUnit and Mockito for testing

## Getting Started

1. Clone the repository: <br/>
`git clone https://github.com/jennysukut/straightforward-job-site-backend.git`
2. Navigate to the project directory:<br/>
`cd straightforward-job-site-backend`
3. Build the project:<br/>
`mvn clean package -DskipTests`
4. Run the application:<br/>
`mvn spring-boot:run`

The application will start on `http://localhost:8082`.

## Configuration

Application configuration can be found in `src/main/resources/application.properties`. Key configurations include:

- Database connection settings
- Logging levels
- External service URLs
- Helcim api key
- Email service provider credentials
- Encryption parameters

## API Documentation

API documentation is available via Altair GQL client.
- Open a new tab
- Enter the URL http://localhost:8082/graphql
- Click on the button on the upper left corner to set a header
- Enter x-api-key for the header key
- Enter whatever for the header value
- Click on the "Docs" in the upper left area to show docs area
- Click on "Reaload Docs" icon to populate the docs area with the schema

At this point you should see Query and Mutation.  You can click on these and drill down to get as much detail as you need.

If you hover over a query or mutation there will be some text that appears that you can click on to add the element to your query.
