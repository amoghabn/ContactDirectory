
# Use the official Ubuntu latest image as the base
FROM ubuntu:latest
RUN apt-get -y update
RUN apt-get -y install git
RUN apt-get install -y openjdk-17-jdk
RUN apt-get install -y mysql-server
RUN apt-get install -y maven 


EXPOSE 3306
CMD ["/usr/sbin/mysqld", "--user=root"]

WORKDIR /root
RUN mkdir personalcontactmanager
COPY . /root/personalcontactmanager


