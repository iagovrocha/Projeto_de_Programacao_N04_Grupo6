FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY src ./src

RUN chmod +x mvnw
RUN sed -i 's/\r$//' mvnw

RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
