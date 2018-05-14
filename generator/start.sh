#!/usr/bin/env bash
if [ $# -eq 0 ]
then
  echo "Missing dataCenterId!";
  exit 1
fi
mvn clean package
java -jar --add-modules=jdk.incubator.httpclient target/generator-0.0.1-SNAPSHOT-jar-with-dependencies.jar http://localhost:8080/api/sensor $1
