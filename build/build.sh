#!/bin/sh

# Cleanup
docker rm -f $(docker ps -a -q)
docker network rm atum-network

# Build app
mvn clean install

# Build images
docker build -t atum-mysql-image -f Dockerfile.mysql .
docker build -t atum-tomcat-image -f Dockerfile.tomcat .

# Create network
docker network create atum-network

# Start containers
docker run --net atum-network -p 3306:3306 --name atum-mysql -d atum-mysql-image
docker run --net atum-network -p 8080:8080 --name atum-tomcat -d atum-tomcat-image
