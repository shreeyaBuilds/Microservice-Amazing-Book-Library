FROM openjdk:11
LABEL maintainer="edureka"
EXPOSE 8087
ADD target/ApplicationGateway-0.0.1-SNAPSHOT.jar ApplicationGateway.jar
ENTRYPOINT ["java", "-jar", "ApplicationGateway.jar"]