FROM openjdk:8-jre-alpine
MAINTAINER Piotr Minkowski <piotr.minkowski@gmail.com>
ADD target/user-service.jar user-service.jar
ENTRYPOINT ["java", "-Xms32m", "-Xmx512m", "-Dspring.profiles.active=prod", "-jar", "/user-service.jar"]
EXPOSE 6666
