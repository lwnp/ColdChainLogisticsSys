FROM container-registry.oracle.com/graalvm/jdk:17
WORKDIR /app
ADD target/user-center-0.0.1-SNAPSHOT.jar .
EXPOSE 8001
LABEL authors="lw230"
ENTRYPOINT ["java", "-jar","/app/user-center-0.0.1-SNAPSHOT.jar"]