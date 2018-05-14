#!/usr/bin/env bash
rm -f generator.jar
mvn -f ../pom.xml clean package
mv ../target/generator-0.0.1-SNAPSHOT-jar-with-dependencies.jar generator.jar
docker build -t mabeulux88/generator:1.0 .
