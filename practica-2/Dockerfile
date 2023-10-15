# Etapa 1: Construir la aplicación
FROM openjdk:17-alpine as builder
WORKDIR /practica-2
COPY . /practica-2
#RUN ./gradlew build
RUN ./gradlew build -x test


# Etapa 2: Ejecutar la aplicación en un contenedor
FROM openjdk:17-alpine
COPY --from=builder /practica-2/build/libs/SpringBoot-CloneMocky-0.0.1-SNAPSHOT.jar /app/CloneMonkey.jar
CMD ["java", "-jar", "/app/CloneMonkey.jar"]

