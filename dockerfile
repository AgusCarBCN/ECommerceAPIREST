# Imagen base con Java 21
FROM eclipse-temurin:21-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR generado al contenedor
COPY target/*.jar app.jar

# Exponer el puerto donde tu API escucha
EXPOSE 8081

# Comando para arrancar la API
ENTRYPOINT ["java","-jar","app.jar"]
