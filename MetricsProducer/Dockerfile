FROM amazoncorretto:21-alpine-jdk
COPY target/*.jar MetricsProducer.jar
ENTRYPOINT ["java","-jar","MetricsProducer.jar"]