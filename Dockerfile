# Use JDK 24 for both build and run
FROM eclipse-temurin:24-jdk as build

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Runtime image
FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
