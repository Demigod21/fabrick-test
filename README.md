# FABRICK-TEST

Project for Fabrick Test

### Requirements

- JDK 11+
- Maven 3.6.3+
- Docker
- Docker Compose

# Way of docker compose + spring

Run

```
docker-compose up 
```

and if all the ports are available, the following applications will start:

- Postgresql: to store transactions
- Adminer: db managing browser app, just go to `http://localhost:8081/`

After that, you can simply start the SpringBoot application with Maven or any IDE that supports it


# How to use it

There's the Url to reach the swagger-ui to test it

`
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/account-controller 
`
