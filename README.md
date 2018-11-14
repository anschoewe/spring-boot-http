# Overview

This application is a very basic Spring Boot 2.0 application.  It has configuration and features designed to make it discoverable in a service mesh.
It shows how to register the application with a locally running Consul agent on port 8500.  

To do this, the application makes use of the [Spring Cloud Consul](http://cloud.spring.io/spring-cloud-consul/single/spring-cloud-consul.html) project.  It is enabled via a Maven depencency.  When the application starts it registers itself with Consul -giving it a unique number after the app name.

This project also makes use of the Spring Actuator project.  It is enabled via Maven dependency.  This exposes a 'health' endpoint that Consul can use to detect if the application is running. 

# Build executable JAR called spring-boot-http and place in /target folder

`mvn clean && mvn package`

# Build docker image
Run this in the main project folder 'spring-boot-http')

Look in the Dockerfile to see the contents of this image.  It's based on OpenDJK 8

`docker build -t spring-boot-http .`

# Run docker container in the background (detached), exposing it on port 8080

`docker run -d -p 8080:8080 --name spring-boot-http spring-boot-http`

# If you need to rebuild your running docker image/container and re-deploy it in docker
Run this in the main project folder 'spring-boot-http'

```
docker stop spring-boot-http && docker rm spring-boot-http
docker build -t spring-boot-http .
docker run -d -p 8080:8080 -p 8443:8443 --name spring-boot-http spring-boot-http
```

# Test in Browser.  
You should see 'Echo: Hello World returned'

`http://localhost:8080/echo/?msg=Hello%20World`

# Test with curl

`curl http://localhost:8080/echo/?msg=Hello%20World`

# Start Consul container, referring this service configuration

```
docker stop consul-agent && docker rm consul-agent
docker run --rm -d -v$(pwd)/consul-services.hcl:/etc/consul/consul-services.hcl \
  --network host --name consul-agent consul:1.3.0 \
  agent -dev -config-file /etc/consul/consul-services.hcl
```

# Inspect logs of consul-agent

`docker logs -f consul-agent`

# Start 'echo' service.  This is the spring-boot-http application.  It must be configured to proxy requests to the side-car Envoy proxy

```
docker stop spring-boot-http && docker rm spring-boot-http
docker run \
-it \
--rm \
-p 8080:8080 \
--name spring-boot-http \
spring-boot-http
```


# Start Envoy side-car proxies for both client (curl) and echo service (spring-boot-http app)

```
docker stop echo-proxy && docker rm echo-proxy
docker stop client-proxy && docker rm client-proxy

docker run --rm -d --network host --name echo-proxy \
  consul-envoy -sidecar-for echo

docker run --rm -d --network host --name client-proxy \
  consul-envoy -sidecar-for client -admin-bind localhost:19001
```

# Now invoke curl (client) which should use it's side-car proxy to send request to the echo/spring-boot service
We need to run the curl command as in a Docker container since we wired every other container together using the docker '--network host' argument.  This means port 9191 is NOT exposed outside of the Docker network

```
docker run --rm --network host appropriate/curl -s http://localhost:9191/echo?msg=Hello%20Andrew2
```

