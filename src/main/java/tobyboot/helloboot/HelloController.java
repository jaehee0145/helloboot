package tobyboot.helloboot;


import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class HelloController {

    public String hello(String name) {
        SimpleHelloService simpleHelloService = new SimpleHelloService();
        return simpleHelloService.sayHello(Objects.requireNonNull(name));
    }
}
