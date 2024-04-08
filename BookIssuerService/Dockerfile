FROM openjdk:11
LABEL maintainer="edureka"
EXPOSE 8088
ADD target/BookIssuerService-0.0.1-SNAPSHOT.jar BookIssuerService.jar
ENTRYPOINT ["java", "-jar", "BookIssuerService.jar"]