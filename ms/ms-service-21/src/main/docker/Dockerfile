# Service-21 docker image
#

FROM java:alpine

VOLUME /tmp
ADD ms-service-21-0.0.1-SNAPSHOT.jar ms-service-21.jar
RUN sh -c 'touch /ms-service-21.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/ms-service-21.jar"]
