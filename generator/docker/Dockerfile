FROM openjdk:10-jre-slim
ADD generator.jar /generator.jar
ENTRYPOINT ["java", "-jar", "--add-modules=jdk.incubator.httpclient", "generator.jar"]
