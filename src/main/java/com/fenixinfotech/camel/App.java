package com.fenixinfotech.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    Logger logger = LoggerFactory.getLogger(App.class);

    @Autowired
    CamelContext camelContext;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {

        logger.info("bootstrapping camel app");

        return args -> {
/*
            // file
            logger.info("adding file route");
            camelContext.addRoutes(new RouteBuilder() {

                @Override
                public void configure() throws Exception {

                    from("file:src/test/routes/data?noop=true")
                            .log(LoggingLevel.INFO, "firing route")
                            .to("file:target/test/routes/output");
                }
            });
*/
            // servlet
            logger.info("adding servlet route");
            camelContext.addRoutes(new RouteBuilder() {

                @Override
                public void configure() throws Exception {
/*
                    restConfiguration().component("servlet")
                            .bindingMode(RestBindingMode.auto)
                            .apiContextPath("/api-doc")
                            .apiProperty("api.title", "User API").apiProperty("api.version", "1.2.3")
                            // and enable CORS
                            .apiProperty("cors", "true");
                    ;
*/
                    rest("ping")
                            .description("Ping service")
                            .get()
//                            .get("/hello")
                            .to("direct:hello");

                    from("direct:hello")
                            .log(LoggingLevel.INFO, "Hello World Direct")
                            .transform().simple("Hello World");
                }
            });
        };
    }
}