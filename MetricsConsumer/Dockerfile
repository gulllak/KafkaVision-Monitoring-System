FROM amazoncorretto:21-alpine-jdk
COPY target/*.jar MetricsConsumer.jar
ENTRYPOINT ["java","-jar","MetricsConsumer.jar"]