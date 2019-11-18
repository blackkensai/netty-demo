FROM openjdk:8-jdk-alpine
COPY target/netty-demo-1.0.0.jar  /netty-demo-1.0.0.jar
EXPOSE 8080
EXPOSE 8000
ENTRYPOINT exec java -jar /netty-demo-1.0.0.jar