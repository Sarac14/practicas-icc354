# Etapa 1: Construir la aplicación
FROM openjdk:17-alpine as builder
WORKDIR /practica-3
COPY . /practica-3
RUN ./gradlew build -x test

# Etapa 2: Construir la aplicación web
FROM node:14 as nodebuilder
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Etapa 3: Ejecutar la aplicación en un contenedor
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /practica-3/build/libs/practica-3-0.0.1-SNAPSHOT.jar ./app.jar
COPY --from=nodebuilder /usr/src/app/dist ./dist
CMD ["java", "-jar", "app.jar"]
