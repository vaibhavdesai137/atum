#!/bin/sh

# Gracefully shutdown all containers
echo "\nStopping all previously started containers..."
docker stop --time=30 $(docker ps -a -q) 

# Remove all containers
echo "\nDeleting all previously created containers..."
docker rm -f $(docker ps -a -q)

# Remove all networks
echo "\nDeleting all previously created networks..."
docker network rm atum-network

echo "\nDone\n"
