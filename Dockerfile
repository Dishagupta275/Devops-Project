FROM openjdk:17
WORKDIR /app
COPY target/bharatbazaar-1.0.0.jar bharatbazaar.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bharatbazaar.jar"]
