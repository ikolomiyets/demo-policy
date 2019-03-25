# Demo Policy Application

Serves a single endpoint at GET `/policies`, returning list of hardcoded objects that
mimic the basic policy information.

## Building the image

In order to build the image run the following

```docker build -t demo-policy .```

Alternatively add the following records to the ~/.gradle/gradle.properties 
file if you have docker installed locally:
```
dockerUrl=unix:///var/run/docker.sock
dockerKeysDir=
```
or the following records if you want to build image by the remote Docker instance
```
dockerUrl=https://<remote_docker_hostname_or_ip>:<remote_docker_port>
dockerKeysDir=<path_to_tls_certificates>
```

and run ```./gradlew clean buildImage```

## Starting the Demo Frontend container

You can start the Demo Customer container using the following command:

```docker run --name demo-policy --network sandbox demo-policy```

The above command assumes that network sandbox exists in your docker environment.

If not it could be created with the following command:

```docker network create sandbox```

Alternatively, use the `deployment/development/docker-compose.yaml` file to
start application using docker-compose.

