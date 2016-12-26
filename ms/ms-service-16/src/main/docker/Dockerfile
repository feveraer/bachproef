# Service-16 docker image
#

FROM java:alpine

VOLUME /tmp
ADD ms-service-16-0.0.1-SNAPSHOT.jar ms-service-16.jar
RUN sh -c 'touch /ms-service-16.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/ms-service-16.jar"]
