# coral-ms
Overview

coral-ms is an application built on spring Boot and Java. The service exposes APIs which allows client to generate account statements by integrating with a legacy upstream system asynchronously that has resource and scalability
constraints .

Features
1. Generate Account statement report asynchronously and share on clients email id
2. Swagger for API documentation


Technologies

Java: 17, 
Spring Boot: 3.3.4, 
Maven, 
RxJava - 3.0.0, 
Database: H2, 
Lombok, 
Swagger for API documentation, 
Spring Data JPA, 
Junit5, Mockito

Getting Started

1. Clone the Repository : 
git clone https://github.com/Dhanushma/coral-ms.git

2. Build the application :
 mvn clean install

3. Run the application :
   mvn spring-boot:run or java -jar target/coral-ms-0.0.1-SNAPSHOT.jar
4. Access the APIs - http://localhost:8081/swagger-ui.html 