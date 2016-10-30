#!/bin/bash

echo "Removing dangling images..."
docker rmi $(docker images --filter "dangling=true" -q)