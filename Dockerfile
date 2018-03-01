FROM openjdk

ARG VERSION=1.0.0-SNAPSHOT

ADD build/libs/demo-policy-${VERSION}.jar /app/
RUN ["mkdir", "/app/logs"]
RUN useradd devops

RUN ["chown", "-R", "devops", "/app"]

USER devops
EXPOSE 8080

CMD ["java", "-jar", "/app/demo-policy-${VERSION}.jar"]

