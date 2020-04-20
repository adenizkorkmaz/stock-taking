# Stock-taking application and API for Pet Fish Co.

This is a Java RESTful Web Service ​that you can keep track of all of aquariums and their fish. 

* Basic rules to put a fish into an aquarium are like below:
    1. Aquariums can vary in glass type, size (litres), and shape.
    2. Fish can vary in species, color, and number of fins.
    3. Some fish can’t go with other fish or in certain aquariums. It's important to unit test this.
        1. Goldfish don’t go with guppies.
        2. Fish with three fins or more don’t go in aquariums of 75 litres or less.

* By default, seven initial aquarium are being inserted to database while application is starting. 
You can see the initial insert script in the file : `resources/data.sql` 

* If you want to use `GALLON` as aquarium size unit, 
you need to set environment variable in the docker-compose.yml file like: `- AQUARIUM_SIZE_TYPE=GALLON`
By default, it is `LITER`. By changing this unit, you can see aquarium size as `GALLON` in response. 

* After running application you can click below links to see API related documentation:

    1. http://localhost:8080/swagger-ui.html#

    2. http://localhost:8080/v2/api-do

Technologies and libraries are basically like below:

1. `Embeded H2` for database
2. `Spring Hateoas` for link support to response dto
3. `Swagger` for API documentation
4. `Junit 5` for tests
5. `Lombok` for data and logging support
6. `Spring data` for persistence and crud support
7. `Docker` for running app in container

How to run locally with docker compose : 
1. `./start.sh` --> running tests, building application jar, and start docker-compose
2. `./stop.sh` --> stop docker container

How to run locally with mvn:
1. `mvn clean install` --> run tests and build application artifact
2. `mvn spring-boot:run` --> run application

How to run locally with docker : 
1. `mvn clean package` --> build application artifact
2. `docker build . -t stock-taking-image` --> build docker image
3. `docker run -p 8080:8080  stock-taking-image` --> run docker image

