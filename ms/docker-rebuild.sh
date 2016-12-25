#!/bin/bash

MS="$1"
SERVICES=("eureka" "ms-service-" "zipkin" "ms-web")
#BUILD_DIRS=("eureka" "ms-service-" "zipkin" "ms-web")

if [[ -n "$MS" ]]; then
  for i in "${!SERVICES[@]}"; do
    if [[ "${MS}" =~ "${SERVICES[$i]}".* ]]; then
      #BUILD_DIR="${BUILD_DIRS[$i]}"
      echo "Stopping ${MS}..."
      docker-compose stop "${MS}"
      echo "Building docker image..."
      mvn -f "./${MS}" clean install -Dmaven.test.skip=true
      echo "Recreating ${MS}..."
      docker-compose up -d "${MS}"
      echo "Removing dangling images..."
      docker rmi $(docker images --filter "dangling=true" -q)
    fi
  done
fi
