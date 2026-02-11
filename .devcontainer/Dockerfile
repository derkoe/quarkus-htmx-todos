FROM eclipse-temurin:25.0.2_10-jdk-alpine

ENV LANG="en_US.UTF-8"

RUN apk update && apk add git maven nodejs npm openssh procps 

RUN adduser -D vscode && mkdir -p /home/vscode/.m2/repository && chown vscode:vscode /home/vscode/.m2/repository
USER vscode

ENV QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://database:5432/todos
