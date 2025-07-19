# Use official Maven image to build the app
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use a lightweight JDK base image to run the app
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render will bind to $PORT)
EXPOSE 8080

# Make sure the app listens to $PORT env var
ENV PORT=8080
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
