package tobyboot.helloboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
public class HellobootApplication {

    public static void main(String[] args) {

        GenericApplicationContext applicationContext = new GenericApplicationContext();
        // 어떤 클래스로 빈을 생성할지 메타 정보 전달
        applicationContext.registerBean(HelloController.class);
        // 컨테이너가 제공하는 DI
        // 인터페이스를 둬서 코드 레벨의 의존 관계를 제거
        // 주입을 통해서 연관관계 생성
        applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.refresh();

        // 서블릿 컨테이너 띄우기
        TomcatServletWebServerFactory serverF = new TomcatServletWebServerFactory();
        // ServletWebServerFactory를 상속
        WebServer webServer = serverF.getWebServer(servletContext -> {
            servletContext.addServlet("front_controller", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

                    if (req.getRequestURI().equals("/hello") && req.getMethod().equals(RequestMethod.GET.name())) {
                        String name = req.getParameter("name");
                        HelloController helloController = applicationContext.getBean(HelloController.class);
                        String ret = helloController.hello(name);
                        // 웹 응답의 3요소
                        resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                        resp.getWriter().print(ret);
                    } else {
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }
                }
//			}).addMapping("/hello"); // 서블릿 지정
            }).addMapping("/*"); // 프론트 컨트롤러로 모든 url 처리

        });
        webServer.start();

        // 프론트 컨트롤러
        // 기본적인 서블릿의 한계
        // 여러개의 서블릿을 각각의 url에 매핑하는데 대부분의 서블릿에서 필요한 코드가 각 서블릿에 중복됨
        // req, res를 처리하는 것이 부자연스럽다?
        // 공통적인 부분을 프론트 컨트롤러에서 처리하자는 의미
        // 인증, 보안, 다국어처리 등을 프런트 컨트롤러에서
    }


}
