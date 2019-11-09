This project is responsible for managing all the price related operations.

## 1 Getting started
These instructions will help you setup the code base and build and run the application on local environment. 

## 2 Prerequisites

### 2.1 Tech Stack
For setting up cart functionality following software should be installed as pre-requisite:
- Java 8
- Spring boot 2.x
- Maven 3.x and above

### 2.2 Tool Installation

The application can be run locally or in a docker container, the requirements for each setup are listed below.

#### 2.2.1 As Docker container
- Run the below command to run multi-container Docker applications comprising Cassandra, Kafka, Zookeeper and Redis

    cd ../core-framework/src/main/docker
    docker-compose up -d    

## 3 Project Setup

### 3.1 Code setup
Run git clone https://pscode.lioncloud.net/commerceoncloud/pricing-service.git to Checkout the code and setup project in IDE.

And then inside Eclipse or STS 
File -> Import -> Maven -> Existing Maven project


### 3.2 Data setup
NA

### 3.3 Configuration
 - For running the application in Local dev environment, use profile as LOCAL.
 
## 4 Project Setup
 
 ### 4.1 Code setup
 Checkout the code and setup project in IDE. Refer https://pscode.lioncloud.net/commerceoncloud for details.
 
 ### 4.2 Data setup
 NA
 ### 4.3 Configuration
  - For running the application in Local dev environment, use profile as LOCAL so the application will pick the configuration from **application_LOCAL.properties**.

## 5 Build & deployment

### 5.1 Build Application
Build Core framework first before building order-command as it has the dependency.
- In IDE right click on "core-framework" module and run "maven clean install"

### 5.2 Update Deployment Artifacts
- Dockerfile is under src/main/docker.
- Deployment and Service.yml is under src/main/kube.

### 5.2 Run Application
Use mvn spring-boot:run to start the following microservices in sequence
* Config Service
* Edge Service
* Pricing Service


In order to save memory, the following services can not be started, they have no great impact on business development.
* Discovery Service

To access from API Gateway access the URL 

Generate the token
`````
curl -X POST \
  http://localhost:8080/auth-service/oauth/token \
  -H 'Authorization: Basic d2ViLWNsaWVudDp3ZWItY2xpZW50LXNlY3JldA==' \
  -d 'password=user&username=user&grant_type=password'
`````

Now use that token in the header for the pricing API calls

`````
curl -X GET \
  http://localhost:12789/pricing/items/a7dab600-f617-11e9-842c-631303ad6c3f \
  -H 'Authorization: bearer a58f1356-ecf9-483d-9f76-afe61dd00b85' \
  -H 'Postman-Token: 426a4347-3fb8-463f-8f61-7f7d2e1745ed' \
  -H 'cache-control: no-cache'
`````

### 6 Swagger documentation 
Refer http://localhost:12789/swagger-ui.html#/Pricing_Service
http://localhost:12789/v2/api-docs


### 7 Design
Pricing functionality is developed using orchestration pattern. Following are the components required to build and develop the application.

1) Spring boot 5


The module is divided into following layers.
 
- Controller layer - This is the layer responsible for validating the input as well as returning appropriate response. 
- Services layer - This layer consist of classes responsible for calling the domain layer to perform business action. This layer acts as facade layer and is responsible for calling domain layer for business logic and repository layer to persist the information to backend storage (cache as well as DB).
- Dao layer - Defines the method to save or retrieve domain object from persistent storage. There should be one repository per aggregate root and all the entity objects should be retrieved in context of aggregate root. This layer contains classes for storing the information to backend system both Redis and Cassandra.

