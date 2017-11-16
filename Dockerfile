FROM openjdk

ADD build/libs/demo-policy-1.0.0-SNAPSHOT.jar /app/
RUN ["mkdir", "/app/logs"]
RUN useradd devops

RUN ["chown", "-R", "devops", "/app"]

USER devops
EXPOSE 8080

CMD ["java", "-jar", "/app/demo-policy-1.0.0-SNAPSHOT.jar"]

