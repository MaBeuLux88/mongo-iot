#!/usr/bin/env bash
if [ $# -eq 0 ]
then
  echo "Missing datacenter argument!";
  exit 1
fi
docker run --rm -d --network iot --network-alias mongo-iot --name mongo-iot-$1 mabeulux88/mongo-iot:1.0 --spring.data.mongodb.uri=mongodb://mongo/test?retryWrites=true --server.port=808$1
