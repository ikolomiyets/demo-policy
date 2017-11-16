# Demo Policy Application

Serves a single endpoint at GET `/policies`, returning list of hardcoded objects that
mimic the basic policy information.

## Building the image

In order to build the image run the following

```docker build -t demo-policy .```

## Starting the Demo Frontend container

You can start the Demo Customer container using the following command:

```docker run --name demo-policy --network sandbox demo-policy```

The above command assumes that network sandbox exists in your docker environment.

If not it could be created with the following command:

```docker network create sandbox```

