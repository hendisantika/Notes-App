# Create a new image for running the application
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
LABEL authors="hendisantika"

RUN mkdir /project

COPY . /project

# Passed from Github Actions
ARG GIT_VERSION_TAG=unspecified
ARG GIT_COMMIT_MESSAGE=unspecified
ARG GIT_VERSION_HASH=unspecified

WORKDIR /project

# You can read these files for the information in your application
RUN echo $GIT_VERSION_TAG > GIT_VERSION_TAG.txt
RUN echo $GIT_COMMIT_MESSAGE > GIT_COMMIT_MESSAGE.txt
RUN echo $GIT_VERSION_HASH > GIT_VERSION_HASH.txt

CMD mvn clean package

#FROM adoptopenjdk/openjdk21:eclipse-temurin-21-alpine
#FROM bellsoft/liberica-openjdk-debian:21
#FROM openjdk:21-slim
FROM amazoncorretto:21-alpine-jdk
LABEL maintainer="hendisantika@yahoo.co.id"

RUN mkdir /app

RUN addgroup -g 1001 -S hendigroup

RUN adduser -S hendi -u 1001

COPY --from=build /project/target/Notes-App-0.0.1.jar /app/backend.jar

WORKDIR /app

RUN chown -R hendi:hendigroup /app

CMD java $JAVA_OPTS -jar backend.jar
