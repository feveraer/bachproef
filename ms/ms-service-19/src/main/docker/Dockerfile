# Service-19 docker image
#

FROM java:alpine

VOLUME /tmp
ADD ms-service-19-0.0.1-SNAPSHOT.jar ms-service-19.jar
RUN sh -c 'touch /ms-service-19.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/ms-service-19.jar"]
