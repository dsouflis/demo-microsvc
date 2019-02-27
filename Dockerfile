FROM openjdk:8u171-jre-alpine
RUN apk --no-cache add curl
CMD java ${JAVA_OPTS} -jar demo-microsvc-0.0.1-SNAPSHOT.jar
HEALTHCHECK --start-period=30s --interval=5s CMD curl -f http://localhost:8081/actuator/health || exit 1
COPY target/demo-microsvc-0.0.1-SNAPSHOT.jar .
EXPOSE 8081