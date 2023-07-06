# Personal Contact Directory

#### Docker URL: https://hub.docker.com/repository/docker/amoghabn/pcdapprepo/general
#### Docker image: amoghabn/pcdapprepo

## Steps to run:
### To start docker container:
  #### 1. To download the Docker image "amoghabn/pcdapprepo" and save it on the host machine
  docker pull amoghabn/pcdapprepo 
  #### 2. To create a container named "cdapp" based on the "amoghabn/pcdapprepo" image, and to map the container's port 8282 to the host's port 8282, and to run the container in detached mode
  docker run --name cdapp -p 8282:8282 -d amoghabn/pcdapprepo tail -f /dev/null
  #### 3. To open a Bash shell inside the running Docker container named "cdapp", allowing the user to execute commands inside the container interactively.
  docker exec -it cdapp /bin/bash
  #### 4. commands to run within the container to start the application
  ##### cd personalcontactmanager
  ##### mvn clean
  ##### mvn install
  ##### cd target
  ##### java -jar personalcontactmanager-0.0.1-SNAPSHOT.jar
  #### 5. To open the application from the browser:
  open http://localhost:8282/
     
@echo off
echo Project Overview
echo ---------------
echo This project aims to provide a robust and secure web application for managing personal contacts.
echo 
echo Technologies Used
echo -----------------
echo - Spring Boot: Used to develop a web application with data persistence using JPA.
echo - MySQL: Database connectivity is implemented using MySQL.
echo - Spring Security: Implements security features to ensure secure access to resources.
echo - Thymeleaf: Server-side rendering of HTML views.
echo - CSS: Used for styling HTML content.
