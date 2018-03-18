#!/bin/sh

# Cleanup
echo "\nStopping all previously started containers..."
docker rm -f $(docker ps -a -q)

echo "\nDeleting previously created networks..."
docker network rm atum-network

# Pull images
echo "\nPulling images..."
docker pull vaibhavdesai137/atum-mysql:master-28f31c
docker pull vaibhavdesai137/atum-tomcat:master-28f31c

# Create network and start containers
echo "\nCreating new networks..."
docker network create atum-network

echo "\nStarting atum-mysql container..."
docker run --net atum-network -p 3306:3306 --name atum-mysql -d vaibhavdesai137/atum-mysql:master-28f31c

echo "\nSleeping for 30 seconds for mysql to be up..."
sleep 30

echo "\nStarting atum-tomcat container..."
docker run --net atum-network -p 8080:8080 --name atum-tomcat -d vaibhavdesai137/atum-tomcat:master-28f31c

echo "\nStarting atum-tomcat container..."
docker ps

echo "\nDone\n"
