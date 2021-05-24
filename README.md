# Person API

## Overview
The project contains RESTful API which provides a service for storing, updating, retrieving and deleting Person
 entities.

## Prerequisite dependencies
1. Maven
2. Java 11
3. Docker

## Building locally
Once you clone the project to your local system, you can build and test. To compile the project and create jar use
 command

```
mvn clean package
```

## Locally run the service using docker

1. Build image

```
docker build -t person-service:1.0.0 .
```

2. Run image

```
docker run -d -p 6060:6060 --name person-service person-service:1.0.0
```

## Test the app
Before testing ensure the service is running by docker run command. Testing the app by entering curl

1. Create Person

```
curl -X POST localhost:6060/person -H "Content-type:application/json" -d "{\"first_name\":\"John\",\"last_name\":\"Keynes\",\"age\":\"29\",\"favourite_colour\":\"red\"}"
```

2. Get all persons

```
curl -G http://localhost:6060/person
```

3. Get person by id

```
curl -G http://localhost:6060/person/{id}
```

4. Update person by id

```
curl -X PUT localhost:6060/person/{id} -H "Content-type:application/json" -d "{\"first_name\":\"John updated\",\"last_name\":\"Keynes updated\",\"age\":\"30\",\"favourite_colour\":\"green\"}"
```

5. Delete person by id

```
curl -X DELETE http://localhost:6060/person/{id}
```

## Stop docker service
1. Stop container

```
docker stop <container-id>
```

2. Remove container

```
docker rm <container-id>
```

## Test the app in Kubernetes(test 1)
Before deploying the image in Kubertetes cluster push the image into docker registry and change the image name as per the docker registry. Here the services is exposed at 3000. I have attached sample deployment file. To run it use command

```
kubectl apply -f person-deployment.yml
```

