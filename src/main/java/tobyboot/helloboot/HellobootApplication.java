package tobyboot.helloboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class HellobootApplication {

    public static void main(String[] args) {

        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
        applicationContext.registerBean(HelloController.class);
        applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.refresh();

        TomcatServletWebServerFactory serverF = new TomcatServletWebServerFactory();
        WebServer webServer = serverF.getWebServer(servletContext -> {
            servletContext.addServlet("dispatcher_servlet",
                    new DispatcherServlet(applicationContext)
            ).addMapping("/*"); // 프론트 컨트롤러로 모든 url 처리

        });
        webServer.start();
    }


}
