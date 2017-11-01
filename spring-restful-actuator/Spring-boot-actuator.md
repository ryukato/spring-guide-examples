# Spring-boot

## Spring Actuator
Spring actuator는 개발 혹은 실 운영에 배포된 애플리케이션을 모니터링하고 관리하기 위한 HTTP 기반의 endpoints, JMX 혹은 원격 쉘(Shell 혹은 Telnet)을 제공한다. Spring boot 기반의 애플리케이션에 actuator 라이브러리(의존성)을 추가하면 auditing, health 그리고 metrics를 파악할 수 있는 기능(endpoints)들이 자동으로 애플리케이션에 추가된다. 

단 actuator의 HTTP endpoint들은 spring-mvc 기반의 애플리케이션에서만 작동한다. 즉, Spring MVC를 사용하지 않는 다면 Jersey 기반의 애플리케이션에선 actuator의 HTTP endpoint들을 사용할 수 없다. 

* [Jersery와 Spring MVC 사용하기](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-use-actuator-with-jersey)

###### Actuator의 정의

```
Actuator는 제조에서 사용하는 용어인데, 제조쪽에서 어떤 물건이나 다른 장비 등을 움직이거나 제어하는데 사용하는 장비를 일컷는다. Actuator는 작은 변화를 주어 많은 양의 제어 모션들을 생성할 수 있다.
```

###### Maven: actuator dependency in spring-boot

```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```

###### Gradle: actuator dependency in spring-boot
```
dependencies {
    compile("org.springframework.boot:spring-boot-starter-actuator")
}
```

### Endpoints
Actuator는 애플리케이션을 제어하고 모니터링 할 수 있는 endpoints라고 하는 HTTP API를 제공한다. 기본적으로 제공되는 endpoint들이 있고, 여기에 직접 정의한 endpoint들을 추가 할 수 있다. 

| ID | Description |Sensitive Default | example |
|-----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|-----------|
| actuator|다른 endpoints들에 대한 정보를 hypermedia-based의 표현으로 제공한다. 단 HATEOAS 라이브러리(spring-boot-starter-hateoas)를 추가해야 한다.| true | /{actuator management context}/actuator|
| auditevents |애플리케이션의 AUTHENTICATION_SUCCESS과 같은 audit events 정보를 제공한다.| true |  /{actuator management context}/auditevents|
| autoconfig |애플리케이션의 자동 설정 보고서를 보여주며, 자동 설정 내용 중 이미 설정된 것과 그렇지 않은 것을 보여준다. | true |  /{actuator management context}/autoconfig|
| beans |애플리케이션의 모든 bean 정보들을 제공한다. | true |  /{actuator management context}/beans|
| configprops |모든 @ConfigurationProperties들의 조합을 제공한다.| true |  /{actuator management context}/configprops|
| dump |Thread dump 파일을 제공한다.| true |  /{actuator management context}/dump|
| env |profiles과 systemEnvironment등과 같은 Spring의 ConfigurableEnvironment  속성들을 제공한다. | true |  /{actuator management context}/env|
| flyway |[Flyway](https://flywaydb.org)를 통해 수행된 database migration 정보를 제공한다. | true |  /{actuator management context}/flyway|
| liquibase |[Liquibase](http://www.liquibase.org/)를 통해 수행된 database migration 정보를 제공한다. | true |  /{actuator management context}/liquibase|
| health | 애플리케이션의 상태(예, UP, DOWN등)에 대한 정보를 표시한다. security를 적용한 상태에서 인증되지 않은 사용자의 요청에는  UP, DOWN 정도의 상태만 표시되며, 인증된 사용자에게는 보다 더 상세한 정보를 제공한다.| false |  /{actuator management context}/health|
| info | 애플리케이션의 app, build, git과 같은 임의의 정보를 제공한다. | false |  /{actuator management context}/info|
| loggers | 애플리케이션에 설정된 logger들의 정보를 제공하며, 특정 로거의 로그 레벨등을 변경할 수 도 있다. 단  logback.xml이 classpath에 존재해야 한다. | true |  /{actuator management context}/loggers, /{actuator management context}/loggers/{loggername} |
| metrics | 로딩된 class 개수, 힙 메모리,  쓰레드 등의 정보를 종합해서 보여주는 **metrics** 정보를 제공한다.| true |  /{actuator management context}/metrics |
| mappings | @RequestMapping을 통해 설정된 API 정보의 조합 목록을 제공한다. | true |  /{actuator management context}/mappings |
| shutdown | 실행 중인 애플리케이션을 정상적으로 종료할 수 있게 해준다. 기본적으로 shutdown endpoint는 비활성화 되어 있다. | true |  /{actuator management context}/shutdown |
| trace | API 요청에 대한 추적 정보를 제공한다. 기본적으로 최근 100건에 대한 정보를 제공한다. | true |  /{actuator management context}/trace |

Spring MVC을 사용한다면 아래의 추가적인 endpoint들을 사용할 수 있다.

| ID | Description |Sensitive Default | example |
|-----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|-----------|
| docs |Actuator endpoint들에 대한 샘플 요청 및 응답을 포함한 문서를 제공한다. 브라우져로 해당 주소를 접근하면 해당 문서를 잘 볼 수 있다. 단 ``` spring-boot-actuator-docs ``` 라이브러리를 추가해야 한다. | false | /{actuator management context}/docs |
| heapdump | GZip 방식으로 압축된 hprof  heap dump 파일을 제공한다. | true | /{actuator management context}/heapdump |
| jolokia | JMX bean들을 HTTP로 노출하여 준다. 단 jolokia 라이브러리를 추가해야 한다. | true | /{actuator management context}/jolokia |
| logfile |  ``` logging.file ```  혹은 ``` logging.path ```을 설정 하였다면, 설정된 logfile의 내용을 제공한다.  | true | /{actuator management context}/logfile |




### Flyway
#### preparation
##### dependencies
###### library

```
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```
###### plugin

```
<plugin>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-maven-plugin</artifactId>
    <configuration>
        <url>jdbc:h2:mem:test</url>
        <user>sa</user>
        <password></password>
    </configuration>
</plugin>
```

##### resources
* classpath:/db/migration
* sql files

```
create table PERSON (
    ID int not null,
    NAME varchar(100) not null
);
```


### Actuator properties

[Appendix A. Common application properties](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#common-application-properties)의 **ACTUATOR PROPERTIES**들을 참고하면 된다. 
