package tobyboot.helloboot;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequestMapping
@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello") // 웹 요청을 처리할 수 있는 컨트롤러를 찾는다.
    @ResponseBody   // dispatcher servlet에서는 string이 리턴되는 경우에 해당 이름의 뷰를 찾는다.
                    // @RestController를 사용하면 @ResponseBody가 적용된다.
    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
