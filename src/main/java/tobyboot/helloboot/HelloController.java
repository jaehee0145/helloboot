package tobyboot.helloboot;


import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    public String hello(String name) {
        return "Hello " + name;
    }
}
