# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy file pom.xml trước để cache các thư viện dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy toàn bộ source code vào image
COPY src ./src/

# Đóng gói ứng dụng
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy file .jar đã được build từ Stage 1 sang Stage 2
COPY --from=build /app/target/backend-api-0.0.1-SNAPSHOT.jar app.jar

# Tạo thư mục uploads thông qua WORKDIR để chuẩn format Docker linter
WORKDIR /app/uploads
WORKDIR /app

# Expose port mà Spring Boot sẽ chạy
EXPOSE 8080

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
