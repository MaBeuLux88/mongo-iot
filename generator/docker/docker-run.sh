#!/usr/bin/env bash
if [ $# -eq 0 ]
then
  echo "Missing datacenter argument!";
  exit 1
fi
docker run --rm -d --network iot --name generator-$1 mabeulux88/generator:1.0 http://mongo-iot:808$1/api/sensor $1
