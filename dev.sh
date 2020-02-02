#!/usr/bin/env bash

if [[ -n "$1" ]] && [[ "$1" == "down" ]]
  then
    #stop keycloak
    docker-compose -f auth-service/docker-compose.yaml down
    #stop gateway kong
    docker build -t kong:1.4.0-oidc ./gateway/
    docker-compose -f gateway/dev/docker-compose.yaml down
    #stop databases
    docker-compose -f databases/docker-compose.yaml down
    #stop kafka
    docker-compose -f kafka/docker-compose.yaml down
  else
    #start keycloak
    docker-compose -f auth-service/docker-compose.yaml up -d
    #start gateway kong
    docker build -t kong:1.4.0-oidc ./gateway/
    docker-compose -f gateway/dev/docker-compose.yaml up -d
    #start databases
    docker-compose -f databases/docker-compose.yaml up -d
    #start kafka
    docker-compose -f kafka/docker-compose.yaml up -d
fi
