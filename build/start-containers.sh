#!/bin/sh

# Cleanup
echo "\nStopping all previously started containers..."
docker rm -f $(docker ps -a -q)

echo "\nDeleting previously created networks..."
docker network rm atum-network

# Create network and start containers
echo "\nCreating new networks..."
docker network create atum-network

echo "\nStarting atum-mysql container..."
docker run --net atum-network -p 3306:3306 --name atum-mysql -d vaibhavdesai137/atum-mysql:master-latest

echo "\nSleeping for 15 seconds for mysql to be up..."
sleep 15

echo "\nStarting atum-tomcat container..."
docker run --net atum-network -p 8080:8080 --name atum-tomcat -d vaibhavdesai137/atum-tomcat:master-latest

echo "\nStarting atum-tomcat container..."
docker ps

echo "\nDone\n"
