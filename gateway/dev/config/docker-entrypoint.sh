#!/usr/bin/env bash
#Plugins OIDC & Cors
curl -i -s -X POST http://localhost:8001/plugins \
    -d name=oidc \
    -d config.client_id=back \
    -d config.client_secret=back \
    -d config.bearer_only=yes \
    -d config.realm=local \
    -d config.filters=notification/ws/ \
    -d config.introspection_endpoint=http://localhost:8180/auth/realms/local/protocol/openid-connect/token/introspect \
    -d config.discovery=http://localhost:8180/auth/realms/master/.well-known/openid-configuration

curl -i -s -X POST http://localhost:8001/plugins \
    -d name=cors \
    -d config.credentials=true
#Plugins OIDC & Cors

#Service Customer
curl -i -s -X POST http://localhost:8001/services \
    -d protocol=http \
    -d host=localhost \
    -d port=8081 \
    -d name=customer \
    -d id=c66db8e9-8b31-4476-93d9-8fd1b2d4a273

curl -X POST http://localhost:8001/routes \
    -H 'Accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
        "id": "94a5913d-2c04-4511-841d-a4806b2f1ecd",
        "service": {
          "id": "c66db8e9-8b31-4476-93d9-8fd1b2d4a273"
        },
        "name": "default-customer",
        "paths": [
          "/customer/*"
        ]
    }'
#Service Customer


#Service Chat
curl -i -s -X POST http://localhost:8001/services \
    -d protocol=http \
    -d host=localhost \
    -d port=8082 \
    -d name=chat-and-notification \
    -d id=c66db8e9-8b31-4476-93d9-8fd1b2d4a274

curl -X POST http://localhost:8001/routes \
    -H 'Accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
        "id": "94a5913d-2c04-4511-841d-a4806b2f1ece",
        "service": {
          "id": "c66db8e9-8b31-4476-93d9-8fd1b2d4a274"
        },
        "name": "default-chat-and-notification",
        "paths": [
          "/notification/*"
        ]
    }'
#Service Chat


#Service Product
curl -i -s -X POST http://localhost:8001/services \
    -d protocol=http \
    -d host=localhost \
    -d port=8083 \
    -d name=product \
    -d id=c66db8e9-8b31-4476-93d9-8fd1b2d4a275

curl -X POST http://localhost:8001/routes \
    -H 'Accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
        "id": "94a5913d-2c04-4511-841d-a4806b2f1ecf",
        "service": {
          "id": "c66db8e9-8b31-4476-93d9-8fd1b2d4a275"
        },
        "name": "default-product",
        "paths": [
          "/product/*"
        ]
    }'
#Service Product


#Service Order
curl -i -s -X POST http://localhost:8001/services \
    -d protocol=http \
    -d host=localhost \
    -d port=8084 \
    -d name=order \
    -d id=c66db8e9-8b31-4476-93d9-8fd1b2d4a276

curl -X POST http://localhost:8001/routes \
    -H 'Accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
        "id": "94a5913d-2c04-4511-841d-a4806b2f1ec1",
        "service": {
          "id": "c66db8e9-8b31-4476-93d9-8fd1b2d4a276"
        },
        "name": "default-order",
        "paths": [
          "/order/*"
        ]
    }'
#Service Order