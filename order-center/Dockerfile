FROM container-registry.oracle.com/graalvm/jdk:17
WORKDIR /app
ADD target/order-center-0.0.1-SNAPSHOT.jar .
EXPOSE 8004
LABEL authors="lw230"
ENTRYPOINT ["java", "-jar","/app/order-center-0.0.1-SNAPSHOT.jar"]