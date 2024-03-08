FROM container-registry.oracle.com/graalvm/jdk:17
WORKDIR /app
ADD target/hardware-center-0.0.1-SNAPSHOT.jar .
EXPOSE 7999
LABEL authors="lw230"
ENTRYPOINT ["java", "-jar","/app/hardware-center-0.0.1-SNAPSHOT.jar"]