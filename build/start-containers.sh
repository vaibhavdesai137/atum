#!/bin/sh

# Cleanup
docker rm -f $(docker ps -a -q)
docker network rm atum-network

# Create network and start containers
docker network create atum-network
docker run --net atum-network -p 3306:3306 --name atum-mysql -d atum-mysql-image
docker run --net atum-network -p 8080:8080 --name atum-tomcat -d atum-tomcat-image
