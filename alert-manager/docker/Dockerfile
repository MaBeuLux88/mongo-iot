FROM openjdk:10-jre-slim
ADD alert-manager.jar /alert-manager.jar
ENTRYPOINT ["java", "-jar", "--add-modules=jdk.incubator.httpclient", "alert-manager.jar"]
