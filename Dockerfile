#build container
FROM openjdk:21-jdk-bullseye AS builder

#Create a directory for our application
WORKDIR /app

COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

#compile jar file in target/day17-workshop-0.0.1-SNAPSHOT.jar 
# window need the follow to make it executable
RUN chmod a+x mvnw
RUN ./mvnw package -Dmaven.test.skip=true
#RUN /app/mvnw package -Dmaven.test.skip=true

#after this line is another container; everything for/run on this container
#run container
FROM openjdk:21-jdk-bullseye

WORKDIR /app_run

# --from=builder : copy from "builder" container
# . : if no rename ; weather.jar to rename
#COPY --from=builder /app/target/day17-workshop-0.0.1-SNAPSHOT.jar .
COPY --from=builder /app/target/practice-test-0.0.1-SNAPSHOT.jar app.jar
COPY --from=builder /app/src/main/resources/static/todos1.txt /app_run/src/main/resources/static/todos1.txt

##run
ENV PORT=8080 

##SPRING_DATA_REDIS_PASSWORD=zPHzmNAFEVxejRCAtQjMWGHDDcpaACbi SPRING_DATA_REDIS_HOST=viaduct.proxy.rlwy.net SPRING_DATA_REDIS_PORT=20185 SPRING_DATA_REDIS_USERNAME=default

EXPOSE ${PORT}
# timeout : by when we should receive response else error
# start-period : how long should docker wait before docker start check
# retries : only after continuous 3 failure then is error (0: healthy ; 1: not healthy)
# || ext 1 : as curl if error might not return 1 (return >=1), so need put exit 1 
# http://127.0.0.1 = http://localhost:
# HEALTHCHECK --interval=30s --timeout=5s --start-period=5s --retries=3 CMD curl http://127.0.0.1:${PORT}/healthz || exit 1

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar
