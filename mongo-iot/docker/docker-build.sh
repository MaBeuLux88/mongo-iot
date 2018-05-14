#!/usr/bin/env bash
rm -f mongo-iot.jar
mvn -f ../pom.xml clean package
mv ../target/*jar mongo-iot.jar
docker build -t mabeulux88/mongo-iot:1.0 .
