# Stage 1: Build
FROM openjdk:17-alpine as builder
WORKDIR /CRUD-SPRINGBOOT
COPY . /CRUD-SPRINGBOOT
RUN ./gradlew build

# Stage 2: Production image
FROM openjdk:17-alpine
VOLUME /app/logs/CRUD-SPRINGBOOT
ARG PORT=8080
ENV SERVER_PORT=${PORT}
EXPOSE ${PORT}
WORKDIR /app
COPY --from=builder /CRUD-SPRINGBOOT/build/libs/CRUD-SPRINGBOOT-0.0.1-SNAPSHOT.jar /app/CRUD-SPRINGBOOT.jar
CMD ["sh", "-c", "java -jar -Dserver.port=${SERVER_PORT} /app/CRUD-SPRINGBOOT.jar"]