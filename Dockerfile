FROM openjdk:17-alpine

WORKDIR /app

COPY build/libs/*.jar app.jar
COPY ~/Animealth/resource/application-prod.yaml /src/main/resources
COPY ~/Animealth/resource/application-oauth.yaml /src/main/resources

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
