# Spring Boot implementation of Citrine test API

## Build

```
mvn clean package docker:build
```

## Unit Test
To execute all unit tests:

```
mvn test
```

To run a single unit test, all the -Dtest option at the end:
```
mvn test -Dtest=ApplicationTests#threeParameterIdentityConversion
```

## Run

To run project locally as docker (after docker is built):

```
docker run -p 9000:9000 citrine
```

To run project locally as jar (after fat jar is built):
```
java -jar citrine-0.0.1-SNAPSHOT.jar
```

# Access

```
http://localhost:9000/units/si?units=(degree/minute)
```