FROM openjdk:11

ARG VERSION=1.1.0-SNAPSHOT

ADD libs/demo-policy-${VERSION}.jar /app/
RUN ln -s /app/demo-policy-${VERSION}.jar /app/demo-policy.jar
RUN ["mkdir", "/app/logs"]
RUN useradd devops

RUN ["chown", "-R", "devops", "/app"]

USER devops
EXPOSE 8080

CMD ["java", "-jar", "/app/demo-policy.jar"]

