# AcademyMovies Backend

## Table of Contents
- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)


## Introduction
This is a Spring Boot application that demonstrates how to integrate with MongoDB, a NoSQL database. The application provides a RESTful API to perform basic CRUD operations on a collection of documents. 

## Prerequisites
Before you begin, ensure you have met the following requirements:
- Java 11 or later
- Maven 3.6 or later
- MongoDB installed and running on your local machine or accessible through a cloud service

## Installation
1. **Clone the repository**
   https://github.com/Augustas900/AcademyMovies1.git`


Build the project

bash
mvn clean install
Configure MongoDB

Ensure MongoDB is running locally or update the connection string in the application.properties file.
Usage
You can start the application by running the main class.

bash
mvn spring-boot:run
Once the application is running, you can access the API endpoints.

API Endpoints
Below are some of the key API endpoints available in the application:

HTTP Method	Endpoint	Description
GET	/api/v1/movies	Retrieve all items
GET	/{imdbId}	Retrieve an item by ID
POST	/api/v1/reviews	Create a new item

Configuration
The application configuration can be found in the src/main/resources/application.properties file. Update the MongoDB URI and other settings as needed.

properties
spring.data.mongodb.uri=mongodb://localhost:27017/yourdatabase
spring.application.name=Spring Boot MongoDB App


bash
mvn spring-boot:run
By default, the application will start on port 8080. You can change this in the application.properties file.


bash
mvn test
Ensure all tests pass before deploying.


Fork the repository.
Create a new branch (git checkout -b feature/YourFeature).
Commit your changes (git commit -m 'Add some feature').
Push to the branch (git push origin feature/YourFeature).
Open a pull request.
