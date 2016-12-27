# Service-20 docker image
#

FROM java:alpine

VOLUME /tmp
ADD ms-service-20-0.0.1-SNAPSHOT.jar ms-service-20.jar
RUN sh -c 'touch /ms-service-20.jar'
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /ms-service-20.jar
