FROM ubuntu:trusty
 
# Prepare installation of Oracle Java 8
ENV JAVA_VER 8
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# Install git, wget, Oracle Java8
RUN echo 'deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main' >> /etc/apt/sources.list && \
    echo 'deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main' >> /etc/apt/sources.list && \
    echo 'deb http://archive.ubuntu.com/ubuntu trusty main universe' >> /etc/apt/sources.list && \
    apt-key adv --keyserver keyserver.ubuntu.com --recv-keys C2518248EEA14886 && \
    apt-get update && \
    apt-get install -y git wget && \
    echo oracle-java${JAVA_VER}-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y --force-yes --no-install-recommends oracle-java${JAVA_VER}-installer oracle-java${JAVA_VER}-set-default && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* && \
    rm -rf /var/cache/oracle-jdk${JAVA_VER}-installer

# Set Oracle Java as the default Java
RUN update-java-alternatives -s java-8-oracle
RUN echo "export JAVA_HOME=/usr/lib/jvm/java-8-oracle" >> ~/.bashrc

# Install maven 3.5.4
RUN wget --no-verbose -O /tmp/apache-maven-3.5.4-bin.tar.gz http://www-eu.apache.org/dist/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz && \
    tar xzf /tmp/apache-maven-3.5.4-bin.tar.gz -C /opt/ && \
    ln -s /opt/apache-maven-3.5.4 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/local/bin  && \
    rm -f /tmp/apache-maven-3.5.4-bin.tar.gz

# Install jetty 9
RUN wget -c http://repo1.maven.org/maven2/org/eclipse/jetty/jetty-distribution/9.3.12.v20160915/jetty-distribution-9.3.12.v20160915.tar.gz &&\
    tar xzf jetty-distribution-9.3.12.v20160915.tar.gz &&\
    mv jetty-distribution-9.3.12.v20160915 jetty9 &&\
    sudo mv jetty9 /opt &&\
    sudo addgroup --quiet --system jetty &&\
    sudo adduser --quiet --system --ingroup jetty --no-create-home --disabled-password jetty &&\
    sudo usermod -c "Jetty 9" -d /opt/jetty9 -g jetty jetty &&\
    sudo chown -R jetty:jetty /opt/jetty9 &&\
    sudo chmod u=rwx,g=rxs,o= /opt/jetty9

# Change directory and clone project ->  &&\ cd /usr/proj2 &&\ git clone
RUN mkdir /usr/proj2 

WORKDIR /usr/proj2

RUN git clone https://github.com/Faria666/trainning2.git

WORKDIR /usr/proj2/trainning2

ENV MAVEN_HOME /opt/maven

EXPOSE 8080

