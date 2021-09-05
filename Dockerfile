FROM openjdk:16.0.72-slim

ARG VERSION=1.2.0-SNAPSHOT

ADD build/libs/demo-policy-${VERSION}.jar /app/
RUN ln -s /app/demo-policy-${VERSION}.jar /app/demo-policy.jar
RUN ["mkdir", "/app/logs"]
RUN useradd devops

RUN ["chown", "-R", "devops", "/app"]

USER devops
EXPOSE 8080

CMD ["java", "-jar", "/app/demo-policy.jar"]

