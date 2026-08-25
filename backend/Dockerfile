FROM amazoncorretto:21 AS build
WORKDIR /app
RUN yum install -y maven
COPY backend/pom.xml .
COPY backend/src ./src
RUN mvn clean package

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]