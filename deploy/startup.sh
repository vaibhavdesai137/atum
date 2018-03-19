#!/bin/sh

# Pull images
echo "\nPulling images..."
docker pull vaibhavdesai137/atum-mysql:$1
docker pull vaibhavdesai137/atum-tomcat:$1

# Create network and start containers
echo "\nCreating new networks..."
docker network create atum-network

echo "\nStarting atum-mysql container..."
docker run --net atum-network -p 3306:3306 --name atum-mysql -d vaibhavdesai137/atum-mysql:$1

echo "\nSleeping for 30 seconds for mysql to be up..."
sleep 30

echo "\nStarting atum-tomcat container..."
docker run --net atum-network -p 8080:8080 --name atum-tomcat -d vaibhavdesai137/atum-tomcat:$1

echo "\nStarting atum-tomcat container..."
docker ps

echo "\nDone\n"
