FROM openjdk:11
VOLUME /tmp

RUN apt-get update && \
    apt-get install -y postgresql-client

ENV DB_HOST localhost
ENV DB_PORT 5432
ENV DB_NAME weatherdatabase
ENV DB_USERNAME nico
ENV DB_PASSWORD leta

EXPOSE 8080
ARG JAR_FILE=target/coding-dojo-spring-boot-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]