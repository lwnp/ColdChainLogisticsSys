FROM container-registry.oracle.com/graalvm/jdk:17
WORKDIR /app
ADD target/sys-authorization-0.0.1-SNAPSHOT.jar .
EXPOSE 9999
LABEL authors="lw230"
ENTRYPOINT ["java", "-jar","/app/sys-authorization-0.0.1-SNAPSHOT.jar"]