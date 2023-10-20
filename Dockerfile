FROM openjdk:21
EXPOSE 8080
ADD /build/libs/*.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]