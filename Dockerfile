FROM amazoncorretto:17
COPY build/libs/*.jar myapp.jar
ENTRYPOINT ["java", "-jar", "/myapp.jar"]