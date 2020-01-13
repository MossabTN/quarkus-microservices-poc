#!/usr/bin/env bash

if [[ -n "$1" ]] && [[ "$1" == "down" ]]
  then
    #stop keycloak
    docker-compose -f auth-sevice/docker-compose.yaml down -v
    #stop gateway kong
    docker build -t kong:1.4.0-oidc ./gateway/
    docker-compose -f gateway/dev/docker-compose.yaml down -v
    #stop kafka
    docker-compose -f kafka/docker-compose.yaml down -v
  else
    #start keycloak
    docker-compose -f auth-sevice/docker-compose.yaml up -d
    #start gateway kong
    docker build -t kong:1.4.0-oidc ./gateway/
    docker-compose -f gateway/dev/docker-compose.yaml up -d
    #start kafka
    docker-compose -f kafka/docker-compose.yaml up -d
fi
