# Service-8 docker image
#

FROM java:alpine

VOLUME /tmp
ADD ms-service-8-0.0.1-SNAPSHOT.jar ms-service-8.jar
RUN sh -c 'touch /ms-service-8.jar'
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /ms-service-8.jar
