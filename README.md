# Urals REST API with PostgreSQL

This is a test project that is a REST API using Spring Boot and PostgreSQL. The project uses Maven and Spring Data JPA with Hibernate.

## Requirements

To run this project, you need to have the following installed:

- Java 17
- Maven
- PostgreSQL

## Getting Started

1. Clone this repository.
2. Create a new PostgreSQL database.
3. Update the `application.properties` file with your database credentials.
4. Run the application.

## REST API Endpoints

The following table lists the REST API endpoints that are available in this project:

| **HTTP Method** | **Endpoint**                                     | **Description**                                                                                                                                        |
|-----------------|--------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET             | /urals-api/by-id/{id}                            | Get full Price by ID                                                                                                                                   |
| GET             | /urals-api/by-date?date=MM/dd/yyyy               | Get Price value by date                                                                                                                                |
| GET             | /urals-api/by-date-full?date=MM/dd/yyyy          | Get full Price by date                                                                                                                                 |
| GET             | /urals-api/average?from=MM/dd/yyyy&to=MM/dd/yyyy | Get average Price value between dates                                                                                                                  |
| GET             | /urals-api/min-max?from=MM/dd/yyyy&to=MM/dd/yyyy | Get minimum and maximum Price values between dates                                                                                                     |
| GET             | /urals-api/stats                                 | Get statistics(min, max Prices with corresponding dates and total amount of entries)                                                                   |
| POST            | /urals-api                                       | Add new Price to repository (must add json with key-values: date ("yyyy-mm-dd"), price, open, high, low, change (#.##%) OR date ("yyyy-mm-dd"), price) |
| DELETE          | /urals-api/{id}                                  | Delete Price by ID.                                                                                                                                    |

For information about possible responses please consider reading JavaDoc for class PriceController
