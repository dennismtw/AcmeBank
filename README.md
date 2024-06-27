### Git Hub Link
https://github.com/dennismtw/AcmeBank.git

Checkout Project and Run AcmeBank\src\main\java\com\acmebank\banking\account\AccountApplication.java

For first time execution, database schema and init data will be executed in resource folder:
schema.sql and data.sql

### Junit Test Case
AccountApplicationTests

### H2 Database:
http://localhost:8080/h2-console/
jdbc:h2:~/acmebank
username: sa
password: ''

Reset db execute below:
DROP ALL OBJECTS

### Swagger UI 
Program can be tested by Swagger UI by accessing below link
http://localhost:8080/swagger-ui.html

### Postman Test
Program can also be tested by importing postman project
account\ACMEBank.postman_collection.json

### Validation and Exception handling 
1. All validation and exception will route to GlobalExceptionHandler class for further process
2. All input format validation will be handled by JR303 Annotation, either on the DTO field or Controller method
3. For custom Business Exception like account not exist and over Draft, is located in exception package and handled in 
GlobalExceptionHandler handleBusinessException method

### Init Customer ID
Customer ID: 1 for Account No:12345678
Customer ID: 2 for Account No:88888888
Customer ID: 3 for Account No:99999999