# Service-7 docker image
#

FROM java:alpine

VOLUME /tmp
ADD ms-service-7-0.0.1-SNAPSHOT.jar ms-service-7.jar
RUN sh -c 'touch /ms-service-7.jar'
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /ms-service-7.jar
