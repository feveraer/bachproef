# Web docker image
#

FROM java:alpine

VOLUME /tmp
ADD ms-web-0.0.1-SNAPSHOT.jar ms-web.jar
RUN sh -c 'touch /ms-web.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/ms-web.jar"]
