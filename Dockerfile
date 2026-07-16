# Build stage — compile inside the image so it's reproducible anywhere
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -B

# Runtime stage — slim image with just the JRE and the fat jar
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/ccm-generator.jar ./ccm-generator.jar
ENTRYPOINT ["java", "-jar", "ccm-generator.jar"]