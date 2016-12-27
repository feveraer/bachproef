# Service-12 docker image
#

FROM java:alpine

VOLUME /tmp
ADD ms-service-12-0.0.1-SNAPSHOT.jar ms-service-12.jar
RUN sh -c 'touch /ms-service-12.jar'
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /ms-service-12.jar
