# Vertical Logistics

### Required installation
* Make 
* Maven (used version 3.9.2)
* Java 11
* docker-compose (used version 1.26.0)

### Structure
* Java 11
* PostgreSQL 11
* Spring Boot 2.6.11

### Frameworks / Libs
* Lombok
* Flyway
* JaCoCo
* Swagger
* Testcontainers

### Build

```shell
λ mvn clean install
```

### Coverage
* Coverage Report is generated by JaCoCo, and is available in `/target/site/jacoco/index.html`

### How to execute
```shell
λ mvn clean install
λ cd development-environment
λ docker-compose up
λ make run
```

### Requests example

* Input data

```shell
curl --location --request POST 'http://localhost:8080/input-orders' \
--form 'file=@"/home/user/dados_import/data_1.txt"'
```

* Find By Order ID

```shell
curl --location --request GET 'http://localhost:8080/orders?orderId=4'
```

* Find by Period

```shell
curl --location --request GET 'http://localhost:8080/orders?startDate=2021-03-08&endDate=2021-03-08'
```
