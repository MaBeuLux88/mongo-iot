#!/usr/bin/env bash
docker run --rm -d -p 27017:27017 --network iot --network-alias mongo --name mongo mongo:3.6.4 $1
