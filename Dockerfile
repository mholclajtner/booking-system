FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} booking-system-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","--enable-preview","-jar","/booking-system-1.0-SNAPSHOT.jar"]