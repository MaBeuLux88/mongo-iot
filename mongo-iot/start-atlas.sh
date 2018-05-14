#!/usr/bin/env bash
if [ $# -eq 0 ]
then
  echo "Missing Atlas URI!";
  exit 1
fi
mvn clean package
java -jar target/mongo-iot-0.0.1-SNAPSHOT.jar --spring.data.mongodb.uri=$1
