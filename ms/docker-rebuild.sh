#!/bin/bash

MS="$1"
SERVICES=("eureka" "ms-service-1" "ms-service-2" "zipkin" "ms-web")
BUILD_DIRS=("eureka" "ms-service-1" "ms-service-2" "zipkin" "ms-web")

if [[ -n "$MS" ]]; then
  for i in "${!SERVICES[@]}"; do
    if [[ "${SERVICES[$i]}" = "${MS}" ]]; then
      BUILD_DIR="${BUILD_DIRS[$i]}"
      echo "Stopping ${MS}..."
      docker-compose stop "${MS}"
      echo "Building docker image..."
      mvn -f "./${BUILD_DIR}" clean install -Dmaven.test.skip=true
      echo "Recreating ${MS}..."
      docker-compose up -d "${MS}"
      echo "Removing dangling images..."
      docker rmi $(docker images --filter "dangling=true" -q)
    fi
  done
fi
