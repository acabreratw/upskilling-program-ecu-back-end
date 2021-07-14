# back-end-template
## Getting Started

## Available Scripts

### `make run`
Runs the app in the development mode.<br />
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

### `make test`
similar to 
### `./gradlew test`
Runs unit tests and store html test results in ./build/reports/tests/test,
xml test results are available in ./build/test-results/tests for pipeline reporting

### `make it`
Runs integration tests

### `make coverage`
Gets coverage report for tests, html results are stored in ./build/reports/jacoco/test/html

### `./gradlew check -Pstrict-security`
Gets dependency vulnerability report in the console and the detailed html 
in ./build/reports/dependency-check-report.html 

## Run Application Locally
you need to have in consideration some things:
* set up the local environment variables :

 >`export DB_HOST=localhost`

 > `export DB_PORT=3306`
  
 > `export DB_NAME=db_greeting`
  
 > `export DB_USERNAME=mysql_user`
  
>  `export DB_PASSWORD=mysql_pw`
  
>  `export AUTH0_AUDIENCE=https://dev-beach-upskilling.us.auth0.com/api/v2/`
  
>  `export AUTH0_ISSUER_URI=https://dev-beach-upskilling.us.auth0.com/`

* run the database locally:
> `docker-compose -f docker-compose-db-local.yml up 

*  execute the migrations in database :

> `docker-compose -f ./migrations/docker-compose-local.yml up -d --build`


* run the application :

> `make run`

Note: you can reach the application in :
 > ` http://localhost:3000/api/v1/<resource>`

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/gradle-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [OAuth2 Resource Server](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-security-oauth2-server)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
