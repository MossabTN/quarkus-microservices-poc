#!/usr/bin/env bash
docker build -t kong:1.4.0-oidc .
docker-compose up -d