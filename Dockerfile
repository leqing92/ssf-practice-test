FROM maven:3-eclipse-temurin-21

WORKDIR /app

COPY mvnw.cmd .
COPY mvnw .
COPY pom.xml .
COPY src src
COPY .mvn .mvn

RUN mvn package -Dmaven.test.skip=true

ENV PORT=3000

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar target/practice-test-0.0.1-SNAPSHOT.jar