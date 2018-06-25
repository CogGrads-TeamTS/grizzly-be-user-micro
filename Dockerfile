FROM openjdk:8-jre-alpine
MAINTAINER Piotr Minkowski <piotr.minkowski@gmail.com>
ADD target/applicationUser-service.jar applicationUser-service.jar
ENTRYPOINT ["java", "-Xms32m", "-Xmx128m", "-Dspring.profiles.active=prod", "-jar", "/applicationUser-service.jar"]
EXPOSE 6666