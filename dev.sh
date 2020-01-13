#!/usr/bin/env bash
#start keycloak
docker-compose -f auth-sevice/docker-compose.yaml up -d
#start gateway kong
docker build -t kong:1.4.0-oidc ./gateway/
docker-compose -f gateway/docker-compose.yaml up -d
#start kafka
docker-compose -f kafka/docker-compose.yaml up -d