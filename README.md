Spring Boot Coding Dojo
---

Welcome to the Spring Boot Coding Dojo!

### Introduction

This is a simple application that requests its data from [OpenWeather](https://openweathermap.org/) and stores the result in a database. 

### Getting Started

### Prerequisites

Java version: 11.0.19
Apache Maven 3.9.2
Docker Desktop 4.20.1
DBeaver 23.1.1

### Installation
Run `mvn clean install` in order to download dependencies to local repo.

### Configuration
Create a database connection in DBeaver using database credentials and url/host and port available in configuration file `src/main/resources/application.yml`

### Usage
Run `docker-compose up -d` in project root folder to start the application.

To stop container run `docker-compose down`

Check Docker desktop for container status.

The application runs on port `8080`

### API Documentation
Once the application is started , you can find api documentation at `http://localhost:8080/v2/api-docs`

### Tests
Manual testing can be performed using Postman.

`WeatherApp.postman_collection.json` can be imported into Postman to make requests to the app.

For unit tests, open Docker Desktop, search postgresql image and pull.

Create/Run container with params:
 POSTGRES_USER=nico 
 POSTGRES_PASSWORD=leta 
 POSTGRES_DB=weathersnapshot
 Host: 5432

Tests are located in `src/test/java/com/assignment/spring`

### Improvements

Use testconteiners framework for testing instead of using mocks.