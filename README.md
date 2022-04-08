# FABRICK-TEST

Project for Fabrick Test

### Requirements

- JDK 11+
- Maven 3.6.3+
- Docker
- Docker Compose

# Way of docker compose

Just run the command

```
docker-compose up 
```

and if all the ports are available, the following applications will start:

- Postgresql: to store donuts and ingredients
- Adminer: db managing browser app, just go to `http://localhost:8081/`
- fabrick-test: the Springboot application

# Spring + docker comspoe

If you want to run the postgres sql seperatly you need to
- Switch docker-compose.yml with docker-compose-alt.yml
- Change the application.properties file to point to localhost instead of postgres 




# How to use it

There's the Url to reach the swagger-ui to test it

```
docker-compose up 
```
