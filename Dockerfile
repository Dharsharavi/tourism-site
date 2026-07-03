FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY target/tourism-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081
EXPOSE 9091

ENTRYPOINT ["java", "-jar", "app.jar"]