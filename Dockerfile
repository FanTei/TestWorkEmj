FROM adoptopenjdk/maven-openjdk11 AS builder

COPY . /app

WORKDIR /app

RUN ["mvn", "clean", "package"]

FROM adoptopenjdk/openjdk11:latest

COPY --from=builder /app/target/TestWorkEmj-0.0.1-SNAPSHOT.jar /application/TestWorkEmj-0.0.1-SNAPSHOT.jar

WORKDIR /application

CMD ["java", "-jar", "TestWorkEmj-0.0.1-SNAPSHOT.jar"]

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]
