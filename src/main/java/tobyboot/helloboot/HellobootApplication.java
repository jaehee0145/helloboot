package tobyboot.helloboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class HellobootApplication {

    public static void main(String[] args) {

        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();
                TomcatServletWebServerFactory serverF = new TomcatServletWebServerFactory();
                WebServer webServer = serverF.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcher_servlet",
                            new DispatcherServlet(this)
                    ).addMapping("/*");

                });
                webServer.start();
            }
        };
        applicationContext.registerBean(HelloController.class);
        applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.refresh();
    }


}
