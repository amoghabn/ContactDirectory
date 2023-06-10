# contactdirectory

#### Docker URL: https://hub.docker.com/repository/docker/amoghabn/pcdapprepo/general
#### Docker image: amoghabn/pcdapprepo

## Steps to run:
### To start docker container:
  #### 1. To download the Docker image "amoghabn/pcdapprepo" and save it on the host machine
  docker pull amohgabn/contactdirectoryimagenew 
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
     

