#!/usr/bin/env bash
mvn clean package
java -jar target/mongo-iot-0.0.1-SNAPSHOT.jar
