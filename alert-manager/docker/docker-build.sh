#!/usr/bin/env bash
rm -f alert-manager.jar
mvn -f ../pom.xml clean package
mv ../target/alert-manager-0.0.1-SNAPSHOT-jar-with-dependencies.jar alert-manager.jar
docker build -t mabeulux88/alert-manager:1.0 .
