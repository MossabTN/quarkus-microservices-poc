#!/usr/bin/env bash

#build native executable
./mvnw clean verify -Pnative

#build docker image
docker build -f ./src/main/docker/Dockerfile.native -t maxilog/customer .