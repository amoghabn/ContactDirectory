# contactdirectory

#### Docker URL: https://hub.docker.com/repository/docker/amohgabn/contactdirectoryimagenew/general
#### Docker image: amohgabn/contactdirectoryimagenew

## Steps to run:
### To start docker container:
  #### 1. To download the Docker image "amohgabn/contactdirectoryimagenew" and save it on the host machine
  docker pull amohgabn/contactdirectoryimagenew 
  #### To create a container named "cdapp" based on the "contactdirectoryimagenew" image, and to map the container's port 8282 to the host's port 8282,            and to run the container in detached mode
  2. docker run --name cdapp -p 8282:8282 -d contactdirectoryimagenew tail -f /dev/null
  #### To open a Bash shell inside the running Docker container named "cdapp", allowing the user to execute commands inside the container interactively.
  3. docker exec -it cdapp /bin/bash
     

