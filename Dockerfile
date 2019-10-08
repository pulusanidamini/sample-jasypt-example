FROM registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift
 
USER root
 
COPY ./target/sample-jasypt.jar /os/sample-jasypt/sample-jasypt.jar
  
RUN chmod a+rw /os/sample-jasypt
 
WORKDIR /os/sample-jasypt
 
ENV JAVA_HOME /etc/alternatives/java_sdk_1.8.0_openjdk
 
USER 1001
 
ENTRYPOINT ["/usr/bin/java","-jar","sample-jasypt.jar"]
