#!/usr/bin/env bash
if [ $# -eq 0 ]
then
  echo "Missing dataCenterId!";
  exit 1
fi
mvn clean package
java -jar target/analyser-0.0.1-SNAPSHOT-jar-with-dependencies.jar mongodb://localhost/test?retryWrites=true $1
