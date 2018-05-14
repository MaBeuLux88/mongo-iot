#!/usr/bin/env bash
rm -f analyser.jar
mvn -f ../pom.xml clean package
mv ../target/analyser-*-jar-with-dependencies.jar analyser.jar
docker build -t mabeulux88/analyser:1.0 .
