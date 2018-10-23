# This is a multi-stage docker build file.
# The first stage, BUILD, uses the Maven base image to compile and package this Java app as spring-boot-http.jar
# The second stage takes the WAR file created in the first stage and copies it into Tomcat image.
# The intermediate BUILD image is then discarded.  You will run the final image built off of Tomcat.

###########################################

FROM maven:3.5-jdk-8-alpine AS BUILD
WORKDIR /spring-boot-http
COPY . .
# Temporarilly removing this to reduce long build times since Maven artifacts are not being cached in the image
# Run 'mvn package' before calling 'docker build...'
#RUN mvn clean package

###########################################

FROM openjdk:8-jdk-alpine AS JAVA

# Copy in executable jar.  Then run it
COPY --from=BUILD /spring-boot-http/target/spring-boot-http.jar spring-boot-http.jar
EXPOSE 8080 8443
CMD ["java", "-jar", "spring-boot-http.jar"]
