# Microservices with Spring Boot - Store :white_check_mark:

Microservices with Spring and best of all with _`MIT license`_:heart_eyes:, so that we can use these projects as a study or as a basis for new projects, even sharing new ideas:bulb:. Feel free to contribute to these projects right here.:heart:

## Index :pushpin:
- [About the project](#about)
- [How to run](#run)
- [Projects and repositories](#prjrepo)
- [License](#license)

## About the project <a name="about"></a> :link:

The project was developed using _`Spring Boot (2.1.5.RELEASE)`_, so it was adopted an architecture based on microservices using all the power of Spring Cloud and its technologies. When we are working with Spring we have several advantages for gaining technologies and solutions already ready to be implemented, so we made use of some of them.

#### Breaking the domain into services

- We broke the domain of the solution into 3 projects _`(loja, fornecedor, transportador)`_, so in our APIs we use some technologies and solutions to build a solid, secure, traceable and scalable architecture.

#### Spring Cloud Netflix Eureka

- We use _`Netflix Eureka`_ as a _`Service Discovery`_ solution, it is very simple and easy to implement.

#### Spring Cloud Config

- The yaml configurations of the projects were all exported and configured through the _`Config Server`_ microservice.

#### Spring Cloud OpenFeign

- _`Spring Feign`_ was used to make calls between microservices in a simple way for its customers, it is a project that was inspired by Retrofit, JAXRS-2.0 and WebSocket. With it we are also able to use the _`Client Side Load Balancer`_ because Feign is integrated with the _`Ribbon`_, which in turn is also integrated with Eureka.

#### Spring Cloud Sleuth

- _`Spring Cloud Sleuth`_ was used to assist us with _`Distributed Tracing`_, responsible for implementing a distributed tracking solution, which helps us track requests between microservices through a correlation ID, so that we can track the entire flow of a request that goes through several microservices. To observe the logs we use _`Papertrail.`_

#### Netflix Hystrix

- We use _`Netflix Hystrix`_ that implements the _`Circuit Breaker`_ standard, which very quickly is a _`failover`_ for calls between microservices, that is, if a microservice is down, a _`fallback`_ method is called and that flood of failures is avoided.
- We also managed to use the _`Bulkhead Pattern`_ using Hystrix's own _`threadPoolKey`_ to isolate the threads and not block our services.

#### Netflix Zuul

- We use _`Spring Zuul`_ as an _`API Gateway`_ because its implementation and its high integration with Netflix Eureka are very simple. Zuul uses Eureka to know the instances of microservices and, using the Ribbon, is able to load balance user requests.

#### Spring Cloud OAuth (OAuth2) - Authentication and authorization between microservices

- We set up all security with Spring Security and _`Spring Cloud OAuth`_ and plugged in through standard spring security adapters with _`OAuth2.`_ The username and password are in memory to facilitate testing.
- A token in the standard _`JSON Web Tokens (JWT)`_ format was implemented.
- For each microservice that we want to assign security, we must configure it in a way that it knows where it must authenticate itself. When a request for the microservice arrives, it simply blocks it, after that it goes to the microservice referring to the [auth](https://github.com/mupezzuol/spring-microservice-auth) security to validate the user's information, to say whether it can access the resource or not, whether that token is valid or not. access. For that we must configure this call.

Microservice [__auth__](https://github.com/mupezzuol/spring-microservice-auth) -> _`application.yml`_
```yaml
security:
  oauth2:
    resource:
      user-info-uri: http://localhost:8088/user
```

- When we are using an _`API Gateway`_ we need to pass the access token from the request that arrives at Zuul to the request that Zuul makes for microservices, for this we configure as follows.

Microservice [__zuul__](https://github.com/mupezzuol/spring-microservice-zuul) -> _`application.yml`_
```yaml
zuul:
  sensitive-headers:
  - Cookie, Authorization
```

- In the [loja](https://github.com/mupezzuol/spring-microservice-loja) microservice when we receive an access token to perform a purchase operation, for example, we need to take that same token and forward it to our calls to customers (Feign) from other microservices, because when we use Feign it will perform new requests to customers, but he does not know the header information originating from the request, so we must configure it so that he knows which header he should pass on to his requests to customers, as the other microservices will need to authenticate as well.

- We have implemented an interceptor to retrieve the request information through the _`SecurityContextHolder`_, making a validation whether or not there is authentication information, if it exists, we were able to redeem the access token value. With the token information in hand we use _`Feign's RestTemplate`_ to add the user's token to the request header, so Feign can pass the token on to his calls.

Microservice [__loja__](https://github.com/mupezzuol/spring-microservice-loja) -> _`SpringMicroserviceLojaApplication.java`_
```java
// Add config to intercept Feign requests for when we call another microservices to be passed the authentication token
@Bean
public RequestInterceptor getInterceptorDeAutenticacao() {
    return new RequestInterceptor() {
        @Override
        public void apply(RequestTemplate template) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null) {
                return;
            }
            
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)authentication.getDetails();
            template.header("Authorization", "Bearer" + details.getTokenValue());
        }
    };
}
```

- It is extremely important to add a configuration to Hystrix so that it can share the security context, if it is disabled it is not possible to forward the token, as Hystrix creates several thread pools.

Microservice [__loja__](https://github.com/mupezzuol/spring-microservice-loja) -> _`application.yml`_
```yaml
hystrix:
  shareSecurityContext: true
```

Below is the flow of OAuth2.
![OAuth2](img/oauth2-fluxo.png)

#### Handling errors in the integration between services

- To deal with this type of error we made a simple implementation, where each step that the microservice store requests for other services we save the request status in the entity, so that if there is Hystrix treatment we can make another request from that state. Here are the status we use: _`RECEIVED`_, _`ORDER_REQUESTED`_ and _`RESERVE_DELIVERED`_.

#### Spring Cloud with Spring Boot

Building distributed systems doesn't need to be complex and error-prone. Spring Cloud offers a simple and accessible programming model to the most common distributed system patterns, helping developers build resilient, reliable, and coordinated applications. Spring Cloud is built on top of Spring Boot, making it easy for developers to get started and become productive quickly.

![Spring Cloud](img/spring-cloud.png)

## How to run <a name="run"></a> :wrench:

Follow the instructions below to build and execute the project in a simple and easy way, but feel free to run the way you want.:relaxed:

#### What you need to have installed

- Java 8;:heart:
- Maven;
- PostgreSQL and MySQL (recommended to use Docker);
- Postman for testing.

#### Database

- We use the _`PostgreSQL`_ database for the microservice __[fornecedor](https://github.com/mupezzuol/spring-microservice-fornecedor)__ and the _`MySQL`_ database for the microservices __[loja](https://github.com/mupezzuol/spring-microservice-loja)__ and __[transportador](https://github.com/mupezzuol/spring-microservice-transportador)__. That way we can work with different databases, we use it that way for study and learning reasons.
- After installation, the respective databases must be created, _`loja, fornecedor and transportador.`_
- Using _`Docker`_ it is very simple to use the databases, see below:

`PostgreSQL`
```docker
docker run --name my-postgres -e "POSTGRES_PASSWORD=postgres" -e "POSTGRES_USER=postgres" -e "PGDATA=/var/lib/postgresql/data/pgdata" -p 5432:5432 -v /your-volumes:/var/lib/postgresql/data -d postgres:9.5
```

`MySQL`
```docker
docker run --name my-mysql -v /your-volumes:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:8.0
```

- Docker Official Images: [PostgreSQL](https://hub.docker.com/_/postgres) and [MySQL](https://hub.docker.com/_/mysql).

#### Build and Run

- Following the order of the microservices below, execute the command via terminal in their respective microservices:

```sh
mvn clean install spring-boot:run -Dmaven.test.skip=true
```

:arrow_heading_down:

- [config-server](https://github.com/mupezzuol/spring-microservice-config-server) _`8888`_
- [eureka-server](https://github.com/mupezzuol/spring-microservice-eureka-server) _`8761`_
- [zuul](https://github.com/mupezzuol/spring-microservice-zuul) _`5555`_
- [boot-admin](https://github.com/mupezzuol/spring-microservice-boot-admin) _`8082`_
- [auth](https://github.com/mupezzuol/spring-microservice-auth) _`8088`_
- [loja](https://github.com/mupezzuol/spring-microservice-loja) _`8080`_
- [fornecedor](https://github.com/mupezzuol/spring-microservice-fornecedor) _`8081`_
- [transportador](https://github.com/mupezzuol/spring-microservice-transportador) _`8083`_

Stay free to use your favorite IDE.:relaxed:

Using _`Spring Tools Suite 4`_ it is very simple to upload microservices.:heart_eyes:
![sts4](img/gif-sts4.gif)

#### Postman

- To perform the services tests, follow the file _`microservices-murillo-pezzuol.postman_collection.json`_ to be imported into Postman located at the root of the repository __[loja](https://github.com/mupezzuol/spring-microservice-loja)__.

Below are some tests performed on Postman.:heart_eyes:
![sts4](img/postman-endpoints.gif)

## Projects and repositories <a name="prjrepo"></a> :file_folder:

#### Applications :computer:

- [spring-microservice-loja](https://github.com/mupezzuol/spring-microservice-loja) - Microservice related to the application of the store. _Tags: `loja`_
- [spring-microservice-fornecedor](https://github.com/mupezzuol/spring-microservice-fornecedor) - Microservice related to supplier application. _Tags: `fornecedor`_
- [spring-microservice-transportador](https://github.com/mupezzuol/spring-microservice-transportador) - Microservice related to the application of the carrier. _Tags: `transportador`_

#### Spring Cloud and Config Server :notebook_with_decorative_cover:

- [spring-microservice-config-server](https://github.com/mupezzuol/spring-microservice-config-server) - Microservice for spring cloud configuration. _Tags: `configuration`_
- [spring-microservice-config-server-repo](https://github.com/mupezzuol/spring-microservice-config-server-repo) - Microservice related to the config server repository. _Tags: `yaml`_

#### Spring Security - OAuth2 :closed_lock_with_key:

- [spring-microservice-auth](https://github.com/mupezzuol/spring-microservice-auth) - Microservice related to the application of authentication and authorization between microservices with OAuth2. _Tags: `OAuth2`, `Security`_

#### Service Registration and Discovery :mag_right:

- [spring-microservice-eureka-server](https://github.com/mupezzuol/spring-microservice-eureka-server) - Microservice related to Netflix Eureka server application. _Tags: `Eureka`_

#### API Gateway :traffic_light:

- [spring-microservice-zuul](https://github.com/mupezzuol/spring-microservice-zuul) - Microservice related to Netflix Zuul server application. _Tags: `proxy`_

#### Monitoring :chart_with_upwards_trend:

- [spring-microservice-boot-admin](https://github.com/mupezzuol/spring-microservice-boot-admin) - Microservice related to microservice monitoring with Spring Boot Admin. _Tags: `actuator`, `swagger`_

## License <a name="license"></a> :clipboard:

Feel free to contribute, we continue with an _`MIT license`_. :heart_eyes:[here](https://github.com/mupezzuol/spring-microservice-loja/blob/master/LICENSE)