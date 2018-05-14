#!/usr/bin/env bash
docker-compose rm -fsv
./docker-build.sh
docker-compose up -d
