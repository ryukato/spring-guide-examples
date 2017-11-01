package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Management endpoints are configured by EndpointAutoConfiguration.
 *
 * Next read: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-metric-writers
 */

@SpringBootApplication
public class RestfulActuatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestfulActuatorApplication.class, args);
    }
}

class Greeting {
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "id=" + id +
                ", content='" + content + "\'" + "}";
    }
}



@Controller
@RequestMapping("/hello-world")
class HelloWorldController {
    private static final String template = "Hello %s";
    private final AtomicLong counter = new AtomicLong();

    private final Repository<Greeting, Long> greetingLongRepository;

    public HelloWorldController(Repository<Greeting, Long> greetingLongRepository) {
        this.greetingLongRepository = greetingLongRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Greeting sayHello(
            @RequestParam(value = "name", required = false, defaultValue = "stranger") String name
    ) {
        return greetingLongRepository.findOne(counter.incrementAndGet());
//       return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody Greeting greeting(@PathVariable("id") Long id) {
        return greetingLongRepository.findOne(id);
    }
}

@Controller
@RequestMapping("/commits")
class CommitController {
    @Value("${git.commit.message.short}")
    private String commitMessage;
    @Value("${git.branch}")
    private String branch;

    @Value("${git.commit.id}")
    private String commitId;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Map<String, String> getCommitId () {
        Map<String, String> result = new HashMap<>();
        result.put("Commit message",commitMessage);
        result.put("Commit branch", branch);
        result.put("Commit id", commitId);
        return result;
    }
}

@Component
class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.up().withDetail("application", "RestfulActuatorApplication").build();
    }
}

// git commit endpoints path: {actuator management context}/info
/*
"git": {
        "branch": "master",
        "commit": {
            "id": "bcc0a6a",
            "time": 1509354157000
        }
    }
*/
@Configuration
class AppConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propsConfig = new PropertySourcesPlaceholderConfigurer();
        propsConfig.setLocation(new ClassPathResource("git.properties"));
        propsConfig.setIgnoreResourceNotFound(true);
        propsConfig.setIgnoreUnresolvablePlaceholders(true);
        return propsConfig;
    }
}

// cache metric
@Configuration
@EnableCaching
class CacheConfiguration {
}

interface Repository<E, ID> {
    E findOne(ID id);
}

@Component
class GreetingRepository implements Repository<Greeting, Long> {
    @Override
    @Cacheable("greetings")
    public Greeting findOne(Long id) {
        return new Greeting(id, "new greeting");
    }
}

// Recording your own metrics, can see counter through {actuator management context}/metrics
// further reading: https://matt.aimonetti.net/posts/2013/06/26/practical-guide-to-graphite-monitoring/
// if custom metrics are required, then implement PublicMetrics, and mark it as bean.
// or can defined own MetricsEndpoint
@Service
class RecordedService {
    private final CounterService counterService;

    public RecordedService(CounterService counterService) {
        this.counterService = counterService;
    }

    public void increaseCounter() {
        this.counterService.increment("mycustom.counter.recordedService.invoked");
    }
}

@Controller
@RequestMapping(value = "record")
class RecordingServiceController {
    private final RecordedService service;

    public RecordingServiceController(RecordedService service) {
        this.service = service;
    }

    @RequestMapping(value = "increase", method = RequestMethod.POST)
    public @ResponseBody void increase() {
        this.service.increaseCounter();
    }
}


@Entity
class Person {
    @Id
    private int id;
    private String name;

    // for JPA
    public Person() {
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return id == person.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}