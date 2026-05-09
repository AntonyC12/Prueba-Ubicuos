FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/evaluacion-backend-0.0.1-SNAPSHOT.jar", "--server.address=0.0.0.0", "--server.port=8080"]
