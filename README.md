#Library Middleware

This is a spring boot project that parses a file (txt and json) to be sent to https://bibliapp.herokuapp.com/explorer/ to add authors and books.

Check https://github.com/gymitoso/library-front for more info.

## Requirements
- Java 8
- Docker https://www.docker.com/

## How to build in Docker
1. Clone this project
2. Run ```chmod +x mvnw```
3. Run ```./mvnw package -DskipTests dockerfile:build```
4. Run ```docker run --restart=always -d -p 8080:8080 --name library-middleware library-middleware```

<strong>User: admin Password: admin</strong>

<strong>Change server port (if necessary) in application.yml and docker run command</strong>

## Bonus
- Jenkinsfile for build and send slack notifications.
- Sonar properties for Sonar analysis.
- Liquibase configuration.
- BDD test with Cucumber https://cucumber.io/
