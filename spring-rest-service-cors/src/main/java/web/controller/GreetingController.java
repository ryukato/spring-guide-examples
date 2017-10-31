package web.controller;

import model.Greeting;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by yoonyoulyoo on 11/24/16.
 */
@RestController
public class GreetingController {

    private static  final String template = "hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    @CrossOrigin(origins="http://localhost:9000")
    public Greeting greeting(@RequestParam(required=false, defaultValue ="world") String name ){
        System.out.println("====== in greeting ======");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/greeting-javaconfig")
    public Greeting greetingWithJavaconfig(@RequestParam(required=false, defaultValue="World") String name) {
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
