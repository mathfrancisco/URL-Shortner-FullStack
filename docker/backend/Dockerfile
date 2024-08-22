# Stage 1: Build the application
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .

RUN mvn dependency:go-offline
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM amazoncorretto:17
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build /build/target/UrlShortner-*.jar /app/

EXPOSE 8080

ENV DB_URL=jdbc:mysql://urls:3306/urls
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

# Define the entrypoint for the container
ENTRYPOINT ["java", "-jar"]

# By default, run the application with the specified profile and database URL
CMD ["-Dspring.profile.active=${ACTIVE_PROFILE}", "-Dspring.datasource.url=${DB_URL}", "UrlShortner-${JAR_VERSION}.jar"]
