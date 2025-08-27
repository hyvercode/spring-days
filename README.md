SPRING BOOT DAYS

1. Spring Days Base

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

2. Spring Days JPA

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

3. Spring Days Swagger

        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-ui</artifactId>
            <version>${springdoc-openapi.version}</version>
        </dependency>

4. Spring Days Security + JWT

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

       <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>${auth0.jwt.version}</version>
        </dependency>

5. Spring Days Open Feign

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
            <version>3.1.4</version>
        </dependency>

6. Spring Days JPA Projection

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
   
7. Spring Days RabbitMQ 

       <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>

8. Spring Days Batch

        <dependency>
            <groupId>org.springframework.batch</groupId>
            <artifactId>spring-batch-infrastructure</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.batch</groupId>
            <artifactId>spring-batch-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.batch</groupId>
            <artifactId>spring-batch-test</artifactId>
            <scope>test</scope>
        </dependency>

9. Spring Days Redis Cache

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

10. Spring Days Firebase Notification

        <dependency>
            <groupId>com.google.firebase</groupId>
            <artifactId>firebase-admin</artifactId>
            <version>9.1.0</version>
        </dependency>

11. Spring Days Mail Sender

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>

12. Spring Days Camunda

        <dependency>
            <groupId>org.camunda.bpm.springboot</groupId>
            <artifactId>camunda-bpm-spring-boot-starter-rest</artifactId>
        </dependency>
        <dependency>
            <groupId>org.camunda.bpm.springboot</groupId>
            <artifactId>camunda-bpm-spring-boot-starter-webapp</artifactId>
        </dependency>
        <dependency>
            <groupId>org.camunda.bpm.model</groupId>
            <artifactId>camunda-dmn-model</artifactId>
            <version>${camunda.version}</version>
        </dependency>
        <dependency>
            <groupId>org.camunda.bpm</groupId>
            <artifactId>camunda-engine-plugin-spin</artifactId>
        </dependency>

        <dependency>
            <groupId>org.camunda.spin</groupId>
            <artifactId>camunda-spin-dataformat-all</artifactId>
        </dependency>

        <dependency>
            <groupId>org.camunda.community.mockito</groupId>
            <artifactId>camunda-platform-7-mockito</artifactId>
            <scope>test</scope>
            <version>6.18.0</version>
        </dependency>


13. Spring Days Google reCAPTCHA

    https://www.google.com/recaptcha/api/siteverify
        
        <dependency>
            <groupId>com.google.firebase</groupId>
            <artifactId>firebase-admin</artifactId>
            <version>9.1.0</version>
        </dependency>


14. Spring Days Resilience4j

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
         <groupId>io.github.resilience4j</groupId>
         <artifactId>resilience4j-spring-boot2</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

15. Spring Days H2 Console

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>

16. Spring Days Change tomcat to Jetty

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>



<br>
API LOGIN
<br>
username :admin
<br>
password :root

CAMUNDA LOGIN
<br>
url : http://localhost:8080/spring-days/camunda/app/welcome/default/#!/login
<br>
username :demo
<br>
password :demo

<br>
Swagger UI
<br>
http://localhost:8080/spring-days/swagger-ui/index.html


H2 Console
<br>
http://localhost:8080/spring-days/h2-console
<br>
jdbc url : jdbc:h2:file:./h2/data/spring_days
<br>
user     : sa
<br>
pass     :     



credit by https://hyvercode.com