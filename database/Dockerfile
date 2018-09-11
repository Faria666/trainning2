
FROM ubuntu:trusty

RUN apt-get update
RUN sudo apt-get -y install wget
RUN apt-get -y install gnupg
RUN apt-get -y install sudo
RUN sudo apt-get -y install software-properties-common
RUN sudo apt -y --fix-broken install python-pycurl python-apt


# Expose the PostgreSQL port
EXPOSE 5432


RUN apt-get -y update
RUN apt-get -y install postgresql postgresql-contrib
RUN update-rc.d postgresql enable
RUN service postgresql start
CMD sudo su - postgres
CMD psql
CMD CREATE USER docker WITH PASSWORD 'docker'
CMD CREATE DATABASE dockerdb OWNER docker

ADD initialize.sh /home/joao-faria/Desktop/jerseytesting/initialize.sh
#CMD ["/home/joao-faria/Desktop/jerseytesting/initialize.sh"]

# Set the default command to run when starting the container
# CMD ["/usr/lib/postgresql/9.3/bin/postgres", "-D", "/var/lib/postgresql/9.3/main", "-c", "config_file=/etc/postgresql/9.3/main/postgresql.conf"]


