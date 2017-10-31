#!/usr/bin/env bash
eval "$(docker-machine env default)"

./mvnw clean package docker:build -Dmaven.test.skip=true

docker rmi $(docker images -f "dangling=true" -q)