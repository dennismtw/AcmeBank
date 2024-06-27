### Git Hub Link
https://github.com/dennismtw/AcmeBank.git

Checkout Project and Run AcmeBank\src\main\java\com\acmebank\banking\account\AccountApplication.java

For first time execution, database schema and init data will be executed in resource folder:
schema.sql and data.sql

### APIs
Three API is created for the project 
First one can get customer Account Balance by customer ID
http://localhost:8080/api/getAccBalByCustId?customerId=1

Second one can get customer Account Balance by Account No
http://localhost:8080/api/accounts/12345678

Third one can do the actual money transfer, with provide from & to account no, and the transfer amount 
http://localhost:8080/api/transfer

### Swagger UI
Program can be tested by Swagger UI by accessing below link
http://localhost:8080/swagger-ui.html

### Postman Test
Program can also be tested by importing postman project with below
account\ACMEBank.postman_collection.json

### Junit Test Case
Junit Test Case can be executed by running AccountApplicationTests,

### H2 Database:
http://localhost:8080/h2-console/
jdbc:h2:~/acmebank
username: sa
password: ''

Reset db execute below:
DROP ALL OBJECTS

### Validation and Exception handling 
1. All validation and exception will route to GlobalExceptionHandler class for further process
2. All input format validation will be handled by JR303 Annotation, either on the DTO field or Controller method
3. For custom Business Exception like Account not exist, Over Draft, Currency not match are located in exception package and handled in 
GlobalExceptionHandler, handleBusinessException method in one place. 

### Table Design
ACCOUNT table will hold the Account Information with Account Type, Balance etc.
TRANSACTION_DETAIL table will record the detail of successful transfer, with Transaction No, date time, amount, sender and receiver  

### Transaction Handling
I used @Transactional annotation in AccountManagerServiceImpl transfer method to make sure both debit and credit amount of both account are within one 
Transaction, this is for monolithic handling. For microservice with distributed transaction cases, we could use other framework, 
e.g. TCC(Try/Confirm/Cancel) with Hmily, Transaction Messaging with Rocket MQ to handle the case.

### Init Customer ID
Customer ID: 1 for Account No:12345678
Customer ID: 2 for Account No:88888888
Customer ID: 3 for Account No:99999999