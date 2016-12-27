# Service-17 docker image
#

FROM java:alpine

VOLUME /tmp
ADD ms-service-17-0.0.1-SNAPSHOT.jar ms-service-17.jar
RUN sh -c 'touch /ms-service-17.jar'
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /ms-service-17.jar
