# 🚀 restaurant-finder

![java](https://img.shields.io/badge/Java-25-blue) 
![spring](https://img.shields.io/badge/Spring_Boot-3.5.6-green) 
![license](https://img.shields.io/badge/license-MIT-lightgrey)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=yolo-deploy_restaurant-finder&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=yolo-deploy_restaurant-finder)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=yolo-deploy_restaurant-finder&metric=coverage)](https://sonarcloud.io/summary/new_code?id=yolo-deploy_restaurant-finder)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=yolo-deploy_restaurant-finder&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=yolo-deploy_restaurant-finder)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=yolo-deploy_restaurant-finder&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=yolo-deploy_restaurant-finder)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=yolo-deploy_restaurant-finder&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=yolo-deploy_restaurant-finder)

Service for management a favorite restaurant. ✨

## ⚙️ Status

- Java 25 • Spring Boot 3.5.6 • MongoDB • JWT authentication 🔐

## ⚡ Quick start

Prerequisites: JDK 25, Maven, running MongoDB

1) Set required environment variables (example):

```cmd
set MONGO_USER=myuser
set MONGO_PASSWORD=mypassword
set MONGO_HOST=localhost
set MONGO_PORT=27017
set MONGO_DATABASE=restaurant-db
set TOKEN_SECRET_KEY=replace_with_secure_key
set TOKEN_EXPIRATION_TIME=3600000
set APP_LOG_LEVEL=INFO
```

2) Build and run:

```cmd
mvn clean package -DskipTests
java -jar target\restaurant-finder.jar
```

Or run in development:

```cmd
mvn spring-boot:run
```

## ⚙️ Configuration

- `src/main/resources/application.yml` maps the following environment variables:
  - `MONGO_USER`, `MONGO_PASSWORD`, `MONGO_HOST`, `MONGO_PORT`, `MONGO_DATABASE`
  - `TOKEN_SECRET_KEY`, `TOKEN_EXPIRATION_TIME`, `APP_LOG_LEVEL`
- Server runs under context path `/restaurant-finder` (default port 8080).


## 📜 License

See `LICENSE` in project root.
