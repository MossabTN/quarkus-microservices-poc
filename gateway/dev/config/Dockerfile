FROM alpine:3.10

RUN apk add --no-cache curl bash

USER root

COPY docker-entrypoint.sh /usr/docker-entrypoint.sh

RUN chmod +x /usr/docker-entrypoint.sh

WORKDIR /

ENTRYPOINT ["./usr/docker-entrypoint.sh"]
