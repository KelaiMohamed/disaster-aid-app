# ---- Build stage ----
# Builds the jar from source inside Docker, so "docker build ." is
# self-contained and doesn't require Maven installed on the host or CI
# runner, and doesn't depend on a separately-run "mvn package" step first.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# Copy only the pom first so dependency resolution is cached in its own
# Docker layer and only re-runs when pom.xml actually changes.
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B package -DskipTests

# ---- Runtime stage ----
# JRE (not JDK) - smaller image, nothing needed at runtime beyond the JVM.
FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S app && adduser -S app -G app
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
RUN chown app:app app.jar
USER app

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
