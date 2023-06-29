FROM amazoncorretto:11-alpine
WORKDIR /app
EXPOSE 5000
COPY target/app.jar .
CMD ["java", "-jar", "app.jar"]