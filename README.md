# **coral-ms**
### Overview

coral-ms is an application built on spring Boot and Java. The service exposes APIs which allows client to generate account statements by integrating with a legacy upstream system asynchronously that has resource and scalability
constraints.

<img width="1512" alt="Screenshot 2024-09-24 at 5 13 27 PM" src="https://github.com/user-attachments/assets/0fe7d12a-7f81-4d9d-9696-44656a53a8b9">


### Features
1. Generate Account statement report asynchronously and share on clients email id
2. Swagger for API documentation

### Assumptions
1. User inputs accountNumber, emailId, fromDate and toDate to generate account statement and expects to receive the statement via email
2. Response has List of transactions, containing transactionId, transactionAmount and transctionDate. transactionType
   (credit/debit) is ignored to make the implementation simple.
3. Implemented as two services, coral-ms(port 8081) and core-bank(port 8082). core-ms application can be devided further into different modules like core, processor, data-access, email service.
4. Used H2 database to store statement request and Transactions
5. Transaction table will be prepopulated on core-bank application start-up with 1000 entries for the
   AccountNumber - 1234567890 and Year - 2024
6. Access H2 console - http://localhost:8082/h2-console , username - 'user' and password - 'password'

### Technologies

* Java: 17
* Spring Boot: 3.3.4
* Maven
* RxJava - 3.0.0 
* Database: H2
* Lombok
* Swagger for API documentation
* Spring Data JPA
* Junit5, Mockito

### Getting Started

#### Run application using maven and Java

1. Clone the Repository :

`git clone https://github.com/Dhanushma/coral-ms.git`

git clone https://github.com/Dhanushma/core-bank.git


2. Build the application :

`mvn clean install`

3. Run the below command:

`mvn spring-boot:run` 

or 

`java -jar target/coral-ms-0.0.1-SNAPSHOT.jar`

`java -jar target/core-bank-0.0.1-SNAPSHOT.jar`

#### Run application using Docker
`docker run -d -p 8082:8082 kddhan/core-bank:0.0.1`

`docker run -d -p 8081:8081 kddhan/coral-ms:0.0.1`

#### Access the APIs - 
http://localhost:8081/swagger-ui.html

http://localhost:8082/swagger-ui.html 
