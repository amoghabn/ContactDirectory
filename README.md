# Personal Contact Directory

#### Docker URL: https://hub.docker.com/repository/docker/amoghabn/pcdapp/general
#### Docker image: amoghabn/pcdapp

## Steps to run:
### To start docker container:
  #### STEP 1. To download the Docker image "amoghabn/pcdapp:v1" and save it on the host machine
  docker pull amoghabn/pcdapp:v1 
  #### STEP 2. To create a container named "cdapp" based on the "amoghabn/pcdapp" image, and to map the container's port 8282 to the host's port 8282, and to run the container in detached mode
  docker run --name cdapp -p 8282:8282 -d amoghabn/pcdapp tail -f /dev/null
  #### STEP 3. To open a Bash shell inside the running Docker container named "cdapp", allowing the user to execute commands inside the container interactively.
  docker exec -it cdapp /bin/bash
  #### STEP 4. commands to run within the container to start the application
  ##### cd personalcontactmanager
  ##### ./setup-mysql.sh
  ##### mvn clean install
  ##### cd target
  ##### java -jar personalcontactmanager-0.0.1-SNAPSHOT.jar
  #### STEP 5. To open the application from the browser:
  open http://localhost:8282/
     
---------------------
### Project Overview
-------------------
#### This project aims to provide a robust and secure web application for managing personal contacts.
 
### Technologies Used
-------------------
#### Spring Boot: Used to develop a web application with data persistence using JPA.
#### Maven: The project is built and managed using Maven, a build automation tool for Java projects.
#### MySQL: Database connectivity is implemented using MySQL.
#### Spring Security: Implements security features to ensure secure access to resources.
#### Thymeleaf: Server-side rendering of HTML views.
#### CSS: Used for styling HTML content.
#### Docker: The application has been containerized using Docker for easy deployment and portability.
