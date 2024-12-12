FROM openjdk:17.0.1-jdk-slim AS build

WORKDIR /app

COPY pom.xml .

COPY src src

COPY settings.xml ./settings.xml

RUN apt-get update && apt-get install -y maven

RUN mvn -B package -s ./settings.xml --file pom.xml

FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8055

ENTRYPOINT ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar", "app.jar"]