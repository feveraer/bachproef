# Service-2 docker image
#

FROM java:alpine

VOLUME /tmp
ADD ms-service-2-0.0.1-SNAPSHOT.jar ms-service-2.jar
RUN sh -c 'touch /ms-service-2.jar'
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /ms-service-2.jar
