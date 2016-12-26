# Service-11 docker image
#

FROM java:alpine

VOLUME /tmp
ADD ms-service-11-0.0.1-SNAPSHOT.jar ms-service-11.jar
RUN sh -c 'touch /ms-service-11.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/ms-service-11.jar"]
