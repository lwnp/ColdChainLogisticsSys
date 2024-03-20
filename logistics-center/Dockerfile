FROM container-registry.oracle.com/graalvm/jdk:17
WORKDIR /app
ADD target/logistics-center-0.0.1-SNAPSHOT.jar .
EXPOSE 8003
LABEL authors="lw230"
ENTRYPOINT ["java", "-jar","/app/logistics-center-0.0.1-SNAPSHOT.jar"]