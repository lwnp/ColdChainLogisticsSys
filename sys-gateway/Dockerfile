FROM container-registry.oracle.com/graalvm/jdk:17
WORKDIR /app
ADD target/sys-gateway-0.0.1-SNAPSHOT.jar .
EXPOSE 8888
LABEL authors="lw230"
ENTRYPOINT ["java", "-jar","/app/sys-gateway-0.0.1-SNAPSHOT.jar"]