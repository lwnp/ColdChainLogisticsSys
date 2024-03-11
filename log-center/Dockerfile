FROM container-registry.oracle.com/graalvm/jdk:17
WORKDIR /app
ADD target/log-center-0.0.1-SNAPSHOT.jar .
EXPOSE 8002
LABEL authors="lw230"
ENTRYPOINT ["java", "-jar","/app/log-center-0.0.1-SNAPSHOT.jar"]