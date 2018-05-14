#!/usr/bin/env bash
if [ $# -eq 0 ]
then
  echo "Missing Slack URL and average temperature threshold!";
  exit 1
fi
mvn clean package
java -jar --add-modules=jdk.incubator.httpclient target/alert-manager-0.0.1-SNAPSHOT-jar-with-dependencies.jar mongodb://localhost/test?retryWrites=true $1 $2
