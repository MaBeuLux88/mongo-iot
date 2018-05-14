#!/usr/bin/env bash
if [ $# -eq 0 ]
then
  echo "Missing Slack URL and average temperature threshold!";
  exit 1
fi
docker run --rm -d --network iot --name alert-manager mabeulux88/alert-manager:1.0 mongodb://mongo/test?retryWrites=true $1 $2
