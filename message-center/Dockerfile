FROM container-registry.oracle.com/graalvm/jdk:17
WORKDIR /app
ADD target/message-center-0.0.1-SNAPSHOT.jar .
EXPOSE 8000
LABEL authors="lw230"
ENTRYPOINT ["java", "-jar","/app/message-center-0.0.1-SNAPSHOT.jar"]