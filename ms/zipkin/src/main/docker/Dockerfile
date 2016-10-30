# Zipkin docker image
#

FROM java:alpine

VOLUME /tmp
ADD zipkin-0.0.1-SNAPSHOT.jar zipkin.jar
RUN sh -c 'touch /zipkin.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/zipkin.jar"]
