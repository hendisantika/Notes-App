# syntax=docker/dockerfile:1

# ---------- Build stage ----------
FROM maven:3.9.16-eclipse-temurin-25-alpine AS build
LABEL authors="hendisantika"

WORKDIR /project

# Cache dependencies first
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src

# Passed from Github Actions
ARG GIT_VERSION_TAG=unspecified
ARG GIT_COMMIT_MESSAGE=unspecified
ARG GIT_VERSION_HASH=unspecified

# You can read these files for the information in your application
RUN echo "$GIT_VERSION_TAG" > GIT_VERSION_TAG.txt \
    && echo "$GIT_COMMIT_MESSAGE" > GIT_COMMIT_MESSAGE.txt \
    && echo "$GIT_VERSION_HASH" > GIT_VERSION_HASH.txt

RUN mvn -B clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:25-jre-alpine
LABEL maintainer="hendisantika@yahoo.co.id"

RUN addgroup -g 1001 -S hendigroup \
    && adduser -S hendi -u 1001 -G hendigroup

WORKDIR /app

COPY --from=build --chown=hendi:hendigroup /project/target/Notes-App-0.0.1.jar /app/backend.jar

USER hendi

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar backend.jar"]
