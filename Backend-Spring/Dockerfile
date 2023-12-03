# Usa una imagen base con JDK 17
FROM eclipse-temurin:17-jdk-hotspot

# Define el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR construido de la aplicación a /app en el contenedor
COPY build/libs/Backend-Spring-1.0.0.jar /app/Backend-Spring-1.0.0.jar

# Expone el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación al iniciar el contenedor
CMD ["java", "-jar", "Backend-Spring-1.0.0.jar"]
