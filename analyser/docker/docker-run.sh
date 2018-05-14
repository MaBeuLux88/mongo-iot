#!/usr/bin/env bash
if [ $# -eq 0 ]
then
  echo "Missing dataCenterId!";
  exit 1
fi
docker run -d --rm --network iot --name analyser-$1 mabeulux88/analyser:1.0 mongodb://mongo/test?retryWrites=true $1
