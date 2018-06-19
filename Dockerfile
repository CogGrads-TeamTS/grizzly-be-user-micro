FROM openjdk:8-jre-alpine
MAINTAINER Piotr Minkowski <piotr.minkowski@gmail.com>
ADD target/vendor-service.jar vendor-service.jar
ENTRYPOINT ["java", "-Xms32m", "-Xmx512m", "-Dspring.profiles.active=prod", "-jar", "/vendor-service.jar"]
EXPOSE 6666