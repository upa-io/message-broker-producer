FROM eclipse-temurin:18

VOLUME /tmp
COPY target/*jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]