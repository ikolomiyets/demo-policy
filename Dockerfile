FROM eclipse-temurin:17.0.8_7-jre as base

ARG VERSION=1.2.0-SNAPSHOT

RUN groupadd -g 1001 devops
RUN useradd -u 1001 -g 1001 devops

ADD build/libs/demo-policy-${VERSION}.jar /app/
RUN ln -s /app/demo-policy-${VERSION}.jar /app/demo-policy.jar
RUN ["mkdir", "/app/logs"]

RUN ["chown", "-R", "devops", "/app"]

USER devops
EXPOSE 8080

FROM base as debug
EXPOSE 5009

CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5009", "-jar", "/app/demo-policy.jar"]


FROM base
CMD ["java", "-jar", "/app/demo-policy.jar"]

