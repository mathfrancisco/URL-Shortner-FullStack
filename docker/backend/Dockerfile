FROM amazoncorretto:17
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY urlshortner/target/urlshortner-*.jar ./urlshortner.jar

EXPOSE 8080

ENV DB_URL=jdbc:mysql://mysql:3306/urls
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

CMD ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-Dspring.datasource.url=${DB_URL}", "urlshortner.jar"]