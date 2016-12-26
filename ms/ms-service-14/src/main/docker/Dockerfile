# Service-14 docker image
#

FROM java:alpine

VOLUME /tmp
ADD ms-service-14-0.0.1-SNAPSHOT.jar ms-service-14.jar
RUN sh -c 'touch /ms-service-14.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/ms-service-14.jar"]
